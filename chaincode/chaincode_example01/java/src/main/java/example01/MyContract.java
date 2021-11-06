package example01;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.ArrayList;

/**
 * Class: MyContract
 */
@Contract(
        name = "soul-diamonds.MyContract",
        info = @Info(
                title = "MyContract",
                description = "SmartContract soul-diamonds - Blockchain Workshop",
                version = "1.0.0",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "2779419602@qq.com",
                        name = "zhirong"
                )
        )
)


@Default
public final class MyContract implements ContractInterface {
    /**
     * Initialize Ledger
     * @param ctx context
     */
    @Transaction(name = "Init", intent = Transaction.TYPE.SUBMIT)
    public void init(final Context ctx) {

        ChaincodeStub stub = ctx.getStub();

        stub.putStringState("Name", "Fabric@Java");
    }

    /**
     * Query Ledger
     * @param ctx context
     * @return name state in ledger
     */
    @Transaction(name = "Hi", intent = Transaction.TYPE.EVALUATE)
    public String hi(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        return stub.getStringState("Name");
    }

    @Transaction(name = "getValue", intent = Transaction.TYPE.EVALUATE)
    public String  getValue(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        return  stub.getStringState(key);
    }

    @Transaction(name = "setValue", intent = Transaction.TYPE.SUBMIT)
    public void setValue(final Context ctx, final String key, final String value) {
        ChaincodeStub stub = ctx.getStub();

        String val = stub.getStringState(key);

        ArrayList values  = null;

        if(val.isEmpty()){
            values = new ArrayList();
        }else{
            values = JSONObject.parseObject(val,ArrayList.class);
        }

        values.add(value);
        stub.putStringState(key, JSON.toJSONString(values));

        values = null; //easy to GC

    }
}
