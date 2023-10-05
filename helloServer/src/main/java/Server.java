import com.testerhome.service.ComputeServer;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;

import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TFramedTransport;

public class Server{

    public static void main(String[] args) {
        try {
            Impl workImpl = new Impl();
            int port = 9019;
            TProcessor tProcessor = new ComputeServer.Processor<ComputeServer.Iface>(workImpl);
            final TNonblockingServerTransport transport = new TNonblockingServerSocket(port);
            TThreadedSelectorServer.Args ttpsArgs = new TThreadedSelectorServer.Args(transport);
            ttpsArgs.transportFactory(new TFramedTransport.Factory());
            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
            ttpsArgs.processor(tProcessor);
            ttpsArgs.selectorThreads(16);
            ttpsArgs.workerThreads(32);
            System.out.println("compute service server on port :" + port);
            TServer server = new TThreadedSelectorServer(ttpsArgs);
            server.serve();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}