'use strict';

const { Contract } = require('fabric-contract-api');

class MyContract extends Contract {

    constructor() {
        super("example01.MyContract");
    }

    // Initialize the chaincode
    async Init(ctx) {
        console.info('========= example01 Init =========');
        await ctx.stub.putState("Name", Buffer.from("Fabric@Node.JS"));
    }

    // query account balance
    async Hi(ctx) {
        let valueBytes = await ctx.stub.getState("Name");
        return `Hello, I'm ${valueBytes.toString()}`;
    }
}

module.exports = MyContract;
