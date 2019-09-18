package com.example.yahyagungorur.saleclient;

import java.net.Socket;

/**
 * Created by YAHYA on 9/10/2019.
 */

public class SocketHandler {
        private static Socket socket;

        public static synchronized Socket getSocket(){
            return socket;
        }

        public static synchronized void setSocket(Socket socket){
            SocketHandler.socket = socket;
        }
}
