package com.aionemu.commons.callbacks.enhancer;

import com.aionemu.commons.utils.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Basic class that checks if class can be transformed. JDK classes are not a subject of transformation.
 *
 * @author SoulKeeper
 */
public abstract class CallbackClassFileTransformer implements ClassFileTransformer {

    private static final Logger log = LoggerFactory.getLogger(CallbackClassFileTransformer.class);

    /**
     * This method analyzes class and adds callback support if needed.
     *
     * @param loader              ClassLoader of class
     * @param className           class name
     * @param classBeingRedefined not used
     * @param protectionDomain    not used
     * @param classfileBuffer     basic class data
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            // no need to scan whole jvm boot classpath
            // also there is no need to transform classes from jvm 'ext' dir
            if (loader == null || loader.getClass().getName().equals("sun.misc.Launcher$ExtClassLoader")) {
                log.trace("Class " + className + " ignored.");
                return null;
            }

            // actual class transformation
            return transformClass(loader, classfileBuffer);
        } catch (Exception e) {

            Error e1 = new Error("Can't transform class " + className, e);
            log.error(e1.getMessage(), e1);

            // if it is a class from core (not a script) - terminate server
            // noinspection ConstantConditions
            if (loader.getClass().getName().equals("sun.misc.Launcher$AppClassLoader")) {
                Runtime.getRuntime().halt(ExitCode.CODE_ERROR);
            }

            throw e1;
        }
    }

    /**
     * Actually transforms the class.
     *
     * @param loader     class loader of this class
     * @param clazzBytes class bytes
     * @return class as byte array if was transformed or null if was not
     * @throws Exception if something went wrong
     */
    protected abstract byte[] transformClass(ClassLoader loader, byte[] clazzBytes) throws Exception;
}
