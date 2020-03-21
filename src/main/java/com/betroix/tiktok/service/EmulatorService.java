package com.betroix.tiktok.service;

import java.io.IOException;

import com.github.unidbg.AndroidEmulator;
import com.github.unidbg.LibraryResolver;
import com.github.unidbg.linux.android.AndroidARMEmulator;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.DalvikModule;
import com.github.unidbg.linux.android.dvm.DvmClass;
import com.github.unidbg.linux.android.dvm.VM;
import com.github.unidbg.linux.android.dvm.array.ByteArray;
import com.github.unidbg.memory.Memory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

public class EmulatorService implements IEmulatorService {
    private AndroidEmulator emulator;
    private VM vm;
    private DvmClass cmsDVM;
    private DvmClass ttEncryptDVM;

    @Autowired
    public EmulatorService(ResourceLoader resourceLoader, EmulatorIOResolver emulatorIOResolver) {
        try {
            emulator = createARMEmulator();
            emulator.getSyscallHandler().addIOResolver(emulatorIOResolver);

            Memory memory = emulator.getMemory();
            memory.setLibraryResolver(createLibraryResolver());
            memory.setCallInitFunction();

            vm = emulator.createDalvikVM(resourceLoader.getResource(EmulatorConstants.APK_PATH).getFile());
            vm.setJni(new EmulatorJni());
            DalvikModule ttEncrypt;
            ttEncrypt = vm.loadLibrary("ttEncrypt", false);

            DalvikModule cms = vm.loadLibrary("cms", false);

            ttEncrypt.callJNI_OnLoad(emulator);
            cms.callJNI_OnLoad(emulator);

            ttEncryptDVM = vm.resolveClass("com/bytedance/frameworks/core/encrypt/TTEncryptUtils");
            cmsDVM = vm.resolveClass("com/ss/sys/ces/a");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private LibraryResolver createLibraryResolver() {
        return new AndroidResolver(23,
                new String[] { "libc.so", "libm.so", "libstdc++.so", "libdl.so", "libnetd_client.so" });
    }

    private AndroidEmulator createARMEmulator() {
        return new AndroidARMEmulator(EmulatorConstants.APP_PACKAGE_NAME);
    }

    public synchronized byte[] ttEncrypt(byte[] x) {
        ByteArray byteArray = new ByteArray(x);
        Number ret = this.ttEncryptDVM.callStaticJniMethod(this.emulator, "handleData([BI)[B",
                this.vm.addLocalObject(byteArray), x.length);
        long hash = ret.intValue() & 4294967295L;
        ByteArray array = (ByteArray) this.vm.getObject(hash);
        this.vm.deleteLocalRefs();
        return (byte[]) array.getValue();
    }

    public synchronized byte[] leviathan(int time, byte[] x) {
        ByteArray byteArray = new ByteArray(x);
        Number ret = this.cmsDVM.callStaticJniMethod(this.emulator, "leviathan(I[B)[B", time,
                this.vm.addLocalObject(byteArray));
        long hash = ret.intValue() & 4294967295L;
        ByteArray array = (ByteArray) this.vm.getObject(hash);
        this.vm.deleteLocalRefs();
        return array.getValue();
    }
}
