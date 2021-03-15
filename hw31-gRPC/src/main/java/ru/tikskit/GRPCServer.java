package ru.tikskit;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.tikskit.service.RemoteValuesProviderImpl;

import java.io.IOException;

public class GRPCServer {
    public static final int SERVER_PORT = 8090;

    public static void main(String... args) throws InterruptedException, IOException {
        RemoteValuesProviderImpl remoteValuesProvider = new RemoteValuesProviderImpl();

        Server server = ServerBuilder.
                forPort(SERVER_PORT).
                addService(remoteValuesProvider).
                build();
        server.start();
        System.out.println("Server is on");
        server.awaitTermination();
    }
}
