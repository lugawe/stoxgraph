package org.stoxgraph.server;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class StoxgraphServer {

    public StoxgraphServer() {}

    static void main(String[] args) {
        Quarkus.run(args);
    }
}
