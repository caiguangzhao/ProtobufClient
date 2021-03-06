package com.caitou.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TransferService implements Runnable {

    private DataReceived mListener;
    private Socket mConnect;

    public TransferService (Socket client, DataReceived listener) {
        this.mConnect = client;
        this.mListener = listener;
    }

    @Override
    public void run() {
        if (mConnect == null)
            return;
        try {
            InputStream is = mConnect.getInputStream();
            while (true) {
                byte[] buffer = new byte[1024];
                int ret;
                if ((ret = is.read(buffer)) != -1) {
                    byte[] data = new byte[ret];
                    for (int i = 0; i < ret; i++) {
                        data[i] = buffer[i];
                    }
                    mListener.onReceived(data);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public interface DataReceived {
        void onReceived(byte[] data);
    }
}