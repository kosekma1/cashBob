/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.server;


import java.rmi.server.*;
import java.io.*;
import java.net.*;

/**
 * Custom RMISocketFactory implemetation allowing server to listen on address
 * configured in configuration file
 * 
 * @author basekjin
 */
public class AnchorSocketFactory extends RMISocketFactory implements
        Serializable {

    private InetAddress ipInterface = null;

    public AnchorSocketFactory() {
    }

    public AnchorSocketFactory(InetAddress ipInterface) {
        this.ipInterface = ipInterface;
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(port, 50, ipInterface);
        return (serverSocket);
    }

    @Override
    public Socket createSocket(String dummy, int port) throws IOException {
        return (new Socket(ipInterface, port));
    }

    @Override
    public boolean equals(Object that) {
        return (that != null && that.getClass() == this.getClass());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.ipInterface != null ? this.ipInterface.hashCode() : 0);
        return hash;
    }
}