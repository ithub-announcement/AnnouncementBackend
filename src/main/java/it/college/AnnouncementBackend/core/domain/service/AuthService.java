package it.college.AnnouncementBackend.core.domain.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itcollege.grpc.authentication.AuthenticationServiceGrpc;
import ru.itcollege.grpc.authentication.JWTPayload;
import ru.itcollege.grpc.authentication.User;

@Service
public class AuthService {
    @Value("${api.url}")
    private String host;

    @Value("${api.port}")
    private int port;

    public String auth(String token) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(this.host, this.port).usePlaintext().build();
        AuthenticationServiceGrpc.AuthenticationServiceBlockingStub stub = AuthenticationServiceGrpc.newBlockingStub(channel);

        // Создаем тело запроса.
        JWTPayload payload = JWTPayload.newBuilder().setAccess(token).build();

        // Отправка запроса и получение пользователя.
        User current = stub.getUserByAccess(payload);

        return current.getUid();
    }
}
