package com.db.code.examples.sampledemo.controller;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AeadConfig;

import java.io.File;
import java.security.GeneralSecurityException;



import com.google.crypto.tink.config.TinkConfig;

public class CryptoWithTink {

    public static void main(String[] args) throws Exception {

        AeadConfig.register();
        try {
            KeysetHandle keysetHandle = KeysetHandle.generateNew(
                    KeyTemplates.get("AES128_GCM"));

            String keysetFilename = "my_keyset.json";
            CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(
                    new File(keysetFilename)));
            Aead aead = keysetHandle.getPrimitive(Aead.class);
            String plaintext="napier";
            String aad="qwerty123";
            System.out.println("Text:"+plaintext);
            byte[] ciphertext = aead.encrypt(plaintext.getBytes(), aad.getBytes());
            System.out.println("Cipher:"+ciphertext.toString());
            byte[] decrypted = aead.decrypt(ciphertext, aad.getBytes());
            String s = new String(decrypted);
            System.out.println("Text:"+s);
        } catch (GeneralSecurityException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

}
