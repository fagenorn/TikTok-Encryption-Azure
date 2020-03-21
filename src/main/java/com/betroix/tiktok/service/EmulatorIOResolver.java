package com.betroix.tiktok.service;

import com.github.unidbg.Emulator;
import com.github.unidbg.file.FileResult;
import com.github.unidbg.file.IOResolver;
import com.github.unidbg.file.linux.AndroidFileIO;
import com.github.unidbg.linux.file.SimpleFileIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmulatorIOResolver implements IOResolver<AndroidFileIO> {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public FileResult<AndroidFileIO> resolve(Emulator<AndroidFileIO> emulator, String pathname, int oflags) {
        FileResult<AndroidFileIO> var10000 = FileResult.failed(-1);
        try {
            if (pathname.equals(EmulatorConstants.INSTALL_PATH)) {

                var10000 = FileResult.success(new SimpleFileIO(oflags,
                        resourceLoader.getResource(EmulatorConstants.APK_PATH).getFile(), pathname));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return var10000;
    }
}
