package ru.tikskit.service;

import io.grpc.stub.StreamObserver;
import ru.tikskit.protobuf.generated.RangeMessage;
import ru.tikskit.protobuf.generated.RemoteValuesProviderGrpc;
import ru.tikskit.protobuf.generated.ValueMessage;

import java.util.concurrent.TimeUnit;


public class RemoteValuesProviderImpl extends RemoteValuesProviderGrpc.RemoteValuesProviderImplBase {

    private static final int SEND_VALUE_RATE = 2;

    @Override
    public void sendMeValues(RangeMessage request, StreamObserver<ValueMessage> responseObserver) {
        if (request.getMin() > request.getMax()) {
            responseObserver.onError(new IllegalArgumentException(String.format(
                    "Lower bound of a range %d is greater than higher %d", request.getMin(), request.getMax())));
            return;
        }



        int curValue = request.getMin();
        do {
            responseObserver.onNext(createValueMessage(curValue));
            curValue += 2;
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(SEND_VALUE_RATE));
            } catch (InterruptedException ignored) { }
        } while (curValue <= request.getMax());
        responseObserver.onCompleted();

    }

    private static ValueMessage createValueMessage(int value) {
        return ValueMessage.newBuilder().
                setValue(value).
                build();
    }
}
