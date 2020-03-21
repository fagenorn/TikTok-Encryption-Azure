package com.betroix.tiktok.service;

import com.github.unidbg.linux.android.dvm.AbstractJni;
import com.github.unidbg.linux.android.dvm.BaseVM;
import com.github.unidbg.linux.android.dvm.DvmClass;
import com.github.unidbg.linux.android.dvm.DvmObject;
import com.github.unidbg.linux.android.dvm.StringObject;
import com.github.unidbg.linux.android.dvm.VM;
import com.github.unidbg.linux.android.dvm.VaList;
import com.github.unidbg.linux.android.dvm.array.ArrayObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public final class EmulatorJni extends AbstractJni {
    private final Logger logger = LoggerFactory.getLogger("JNI");

    @Override
    public DvmObject<?> callObjectMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        this.logger.warn("----- callObjectMethodV ----- [{}]", signature);

        if (signature.equals("java/lang/Thread->getStackTrace()[Ljava/lang/StackTraceElement;")) {
            String[] stackClassNames = new String[] { "dalvik.system.VMStack", "java.lang.Thread", "java.lang.Thread",
                    "kotlin.jvm.internal.h", "kotlin.jvm.internal.h", "com.bytedance.ies.ugc.aweme.network.a$g",
                    "com.bytedance.ttnet.utils.RequestTicketUtil", "com.bytedance.ttnet.retrofit.SsInterceptor",
                    "com.bytedance.frameworks.baselib.network.http.retrofit.BaseSsInterceptor",
                    "com.bytedance.frameworks.baselib.network.http.retrofit.a",
                    "com.bytedance.frameworks.baselib.network.http.retrofit.BaseSsInterceptor",
                    "com.bytedance.retrofit2.intercept.a", "com.bytedance.retrofit2.intercept.b",
                    "com.bytedance.retrofit2.intercept.a", "com.bytedance.retrofit2.SsHttpCall",
                    "com.bytedance.retrofit2.l", "com.bytedance.retrofit2.SsHttpCall",
                    "com.bytedance.retrofit2.SsHttpCall$1", "com.ss.android.ugc.aweme.net.a",
                    "java.util.concurrent.ThreadPoolExecutor", "java.util.concurrent.ThreadPoolExecutor$Worker",
                    "java.lang.Thread", "com.ss.android.ugc.aweme.net.q$a$1" };
            ArrayList<DvmObject<?>> stackTraceObjs = new ArrayList<DvmObject<?>>();

            for (int var9 = 0; var9 < stackClassNames.length; ++var9) {
                String className = stackClassNames[var9];
                StackTraceElement stackTrace = new StackTraceElement(className, "", "", 0);
                stackTraceObjs.add(vm.resolveClass("java/lang/StackTraceElement").newObject(stackTrace));
            }

            return new ArrayObject(stackTraceObjs.toArray(new DvmObject<?>[stackTraceObjs.size()]));
        }

        if (signature.equals("java/lang/StackTraceElement->getClassName()Ljava/lang/String;")) {
            StackTraceElement sTraceElement = (StackTraceElement) dvmObject.getValue();
            String className = sTraceElement.getClassName();

            return new StringObject((VM) vm, className);
        }

        return super.callObjectMethodV(vm, dvmObject, signature, vaList);
    }

    @Override
    public int getStaticIntField(BaseVM vm, DvmClass dvmClass, String signature) {
        this.logger.warn("----- getStaticIntField ----- [{}]", signature);
        return super.getStaticIntField(vm, dvmClass, signature);
    }

    @Override
    public DvmObject<?> getObjectField(BaseVM vm, DvmObject dvmObject, String signature) {
        this.logger.warn("----- getObjectField ----- [{}]", signature);
        DvmObject<?> var10000 = super.getObjectField(vm, dvmObject, signature);
        return var10000;
    }

    @Override
    public int callIntMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        this.logger.warn("----- callIntMethodV ----- [{}]", signature);
        return super.callIntMethodV(vm, dvmObject, signature, vaList);
    }

    @Override
    public long callLongMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        this.logger.warn("----- callLongMethodV ----- [{}]", signature);
        return super.callLongMethodV(vm, dvmObject, signature, vaList);
    }

    @Override
    public boolean callBooleanMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        this.logger.warn("----- callBooleanMethodV ----- [{}]", signature);
        return super.callBooleanMethodV(vm, dvmObject, signature, vaList);
    }

    @Override
    public long callStaticLongMethodV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        this.logger.warn("----- callStaticLongMethodV ----- [{}]", signature);
        return super.callStaticLongMethodV(vm, dvmClass, signature, vaList);
    }

    @Override
    public int callStaticIntMethodV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        this.logger.warn("----- callStaticIntMethodV ----- [{}]", signature);
        return super.callStaticIntMethodV(vm, dvmClass, signature, vaList);
    }

    @Override
    public DvmObject<?> callStaticObjectMethodV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        this.logger.warn("----- callStaticObjectMethodV ----- [{}]", signature);
        if (signature.equals("java/lang/Thread->currentThread()Ljava/lang/Thread;")) {
            return vm.resolveClass("java/lang/Thread").newObject(null);
        }

        return super.callStaticObjectMethodV(vm, dvmClass, signature, vaList);
    }

    @Override
    public boolean callStaticBooleanMethodV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        this.logger.warn("----- callStaticBooleanMethodV ----- [{}]", signature);
        return super.callStaticBooleanMethodV(vm, dvmClass, signature, vaList);
    }

    @Override
    public DvmObject<?> newObjectV(BaseVM vm, DvmClass dvmClass, String signature, VaList vaList) {
        this.logger.warn("----- newObjectV ----- [{}]", signature);
        DvmObject<?> var10000 = super.newObjectV(vm, dvmClass, signature, vaList);
        return var10000;
    }

    @Override
    public void callVoidMethodV(BaseVM vm, DvmObject dvmObject, String signature, VaList vaList) {
        this.logger.warn("----- callVoidMethodV ----- [{}]", signature);
        super.callVoidMethodV(vm, dvmObject, signature, vaList);
    }
}
