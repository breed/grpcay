package bcr33d.grpcay.grpcay;

import bcr33d.grpcay.GrpcayProto;
import bcr33d.grpcay.GrpcayServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;
import net.devh.boot.grpc.server.service.GrpcService;

@Component
@GrpcService
public class GrpcayServer extends GrpcayServiceGrpc.GrpcayServiceImplBase {
    private static String toPigLatin(String message) {
        String[] words = message.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                int end = word.length();
                while (end > 0 && !Character.isLetter(word.charAt(end - 1))) {
                    end--;
                }
                var suffix = word.substring(end);
                word = word.substring(0, end);
                char firstLetter = word.charAt(0);
                if (firstLetter == 'a' || firstLetter == 'e' || firstLetter == 'i' || firstLetter == 'o' || firstLetter == 'u') {
                    result.append(word).append("yay");
                } else {
                    result.append(word.substring(1)).append(firstLetter).append("ay");
                }
                result.append(suffix);
                result.append(" ");
            }
        }
        return result.toString().trim();
    }
    @Override
    public void translate(GrpcayProto.TranslateRequest request, StreamObserver<GrpcayProto.TranslateResponse> responseObserver) {
       if (!request.getTranslateTo().equals("PigLatin")) {
           responseObserver.onNext(GrpcayProto.TranslateResponse.newBuilder()
                   .setErrorMessage("I can only translate to PigLatin")
                   .build());
       } else {
              responseObserver.onNext(GrpcayProto.TranslateResponse.newBuilder()
                     .setTranslatedMessage(toPigLatin(request.getMessage()))
                     .build());
       }
       responseObserver.onCompleted();
    }
}
