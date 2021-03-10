package ru.tikskit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.tikskit.protobuf.generated.RangeMessage;
import ru.tikskit.protobuf.generated.RemoteValuesProviderGrpc;
import ru.tikskit.protobuf.generated.ValueMessage;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8090;


    private static final int NO_VALUE_FROM_SERVER = -1;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 30;

    private static final int CHECK_VALUE_RATE = 1;



    public static void main(String... args) throws InterruptedException {
        AtomicInteger lastValue = new AtomicInteger(NO_VALUE_FROM_SERVER);
        AtomicBoolean done = new AtomicBoolean(false);

        ManagedChannel channel = ManagedChannelBuilder.
                forAddress(SERVER_HOST, SERVER_PORT).
                usePlaintext().
                build();


        RemoteValuesProviderGrpc.RemoteValuesProviderStub stub = RemoteValuesProviderGrpc.newStub(channel);
        stub.sendMeValues(createRangeMessage(MIN_VALUE, MAX_VALUE), new StreamObserver<ValueMessage>() {
            @Override
            public void onNext(ValueMessage value) {

                System.out.println(String.format("%s New value: %d", Calendar.getInstance().getTime().toString(),
                        value.getValue()));
                lastValue.set(value.getValue());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t.getMessage());
                t.printStackTrace();

            }

            @Override
            public void onCompleted() {
                System.out.println("server is done");
                done.set(true);
            }
        });

        Thread th = new Thread(() -> {
            int currentValue = 0;
            int lastValueL = NO_VALUE_FROM_SERVER;
            for (int i = 0; i <= 50; i++) {
                if (done.get()) {
                    System.out.println("client is stopped");
                    return;
                }
                lastValueL = lastValue.get();
                if (lastValueL == NO_VALUE_FROM_SERVER) {
                    currentValue = currentValue + 1;
                    System.out.println(String.format("%s currentValue:%d", Calendar.getInstance().getTime().toString(),
                            currentValue));
                }
                if (lastValueL != NO_VALUE_FROM_SERVER) {
                    currentValue = currentValue + lastValueL + 1;
                    System.out.println(String.format("%s currentValue:%d", Calendar.getInstance().getTime().toString(),
                            currentValue));
                    lastValue.set(NO_VALUE_FROM_SERVER);
                }

                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(CHECK_VALUE_RATE));
                } catch (InterruptedException e) {
                    System.out.println("client> client was interrupted");
                    Thread.currentThread().interrupt();
                    return;
                }


            }
        });

        th.start();
        th.join();


        channel.shutdown();
    }

    private static RangeMessage createRangeMessage(int minValue, int maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException(String.format(
                    "Lower bound of a range %d is greater than higher %d", minValue, maxValue));
        }
        return RangeMessage.newBuilder().
                setMin(minValue).
                setMax(maxValue).
                build();
    }

}
