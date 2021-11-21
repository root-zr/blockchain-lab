package example01;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        ArrayList<String> keyList = new ArrayList<>();
        stub.putStringState("keyList",JSONObject.toJSONString(keyList));
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
        stub.putStringState(key, JSONObject.toJSONString(values));
        values = null; //easy to GC

        String keyList = stub.getStringState("keyList");
        //add key to keyList
        ArrayList<String> keyListObject = JSONObject.parseObject(keyList, ArrayList.class);
        keyListObject.add(key);
        stub.putStringState("keyList",JSONObject.toJSONString(keyListObject));

    }

    @Transaction(name = "queryAll", intent = Transaction.TYPE.EVALUATE)
    public String queryAll(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        Map<String,List> map = new HashMap<>();

        String keyList = stub.getStringState("keyList");
        ArrayList<String> keyListObject = JSONObject.parseObject(keyList, ArrayList.class);

        for(String key:keyListObject){
            String val = stub.getStringState(key);

            ArrayList values  = null;

            if(val.isEmpty()){
                values = new ArrayList();
            }else{
                values = JSONObject.parseObject(val,ArrayList.class);
            }


            map.put(key,values);

        }


        return JSONObject.toJSONString(map);
    }

    @Transaction(name = "getKey", intent = Transaction.TYPE.EVALUATE)
    public String getKey(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        return stub.getStringState("keyList");
    }

}

