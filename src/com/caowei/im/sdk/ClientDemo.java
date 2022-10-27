package com.caowei.im.sdk;

import com.caowei.im.sdk.face.IMSConnectStatusListener;
import com.caowei.im.sdk.face.OnEventListener;
import com.caowei.im.sdk.message.IMSMsg;
import com.im.InetAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientDemo {
    public static void main(String[] args) {
        InetAddress address = new InetAddress(InetAddress.IP, InetAddress.PORT);
        List<InetAddress> addressList = new ArrayList<>();
        addressList.add(address);
        IMSClientFactory.getIMSClient().init(addressList,
                new OnEventListener() {
                }, new IMSConnectStatusListener() {
                    @Override
                    public void onConnecting() {
                    }

                    @Override
                    public void onConnected() {
                    }

                    @Override
                    public void onConnectFailed() {
                    }

                    @Override
                    public void onDisconnect() {
                    }
                });
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            IMSMsg message = new IMSMsg.Builder()
                    .setContent(scanner.nextLine())
                    .build();
            IMSClientFactory.getIMSClient().sendMessage(message);
        }
    }
}
