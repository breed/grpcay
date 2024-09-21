import grpc
import grpcay_pb2_grpc as grpc_pub
import grpcay_pb2 as grpcay_pb

with grpc.insecure_channel('api.ravlykmail.com:5555') as channel:
    stub = grpc_pub.GrpcayServiceStub(channel)
    response = stub.translate(grpcay_pb.TranslateRequest(translateTo='Latin', message='Hello World!'))
    print(response)
    response = stub.translate(grpcay_pb.TranslateRequest(translateTo='PigLatin', message='Hello World!'))
    print(response)
