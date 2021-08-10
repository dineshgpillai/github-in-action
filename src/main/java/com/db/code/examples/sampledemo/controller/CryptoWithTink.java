package com.db.code.examples.sampledemo.controller;

import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AeadConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.GeneralSecurityException;



import com.google.crypto.tink.config.TinkConfig;
import org.springframework.util.SerializationUtils;

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

            //try for an Java object
            Sample obj = new Sample();
            obj.display();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            byte [] data = bos.toByteArray();

            byte[] cipherObject = aead.encrypt(data, data);
            System.out.println("Cipher Object:"+cipherObject.toString());
            byte[] decryptedObject = aead.decrypt(cipherObject, data);

            Sample newObj = (Sample)SerializationUtils.deserialize(decryptedObject);
            newObj.display();



        } catch (GeneralSecurityException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

}

class Sample implements Serializable {
    public void display() {
        System.out.println("This is a sample class");
    }
}

class ObjectToByteArray {
    public static void main(String args[]) throws Exception {
        Sample obj = new Sample();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte [] data = bos.toByteArray();
    }
}
