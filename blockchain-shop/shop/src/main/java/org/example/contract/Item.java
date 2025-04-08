package org.example.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class Item extends Contract {
    public static final String FUNC_ALLOWACCESS = "allowAccess";
    public static final String FUNC_DISALLOWACCESS = "disallowAccess";
    public static final String FUNC_ISALLOWACCESS = "isAllowAccess";
    public static final String FUNC_MINT = "mint";
    public static final String FUNC_OWNER = "owner";
    public static final String FUNC_OWNEROF = "ownerOf";
    public static final String FUNC_TRANSFER = "transfer";
    public static final String FUNC_TRANSFERFORCE = "transferForce";
    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    private static final String BINARY = "0x60a06040523373ffffffffffffffffffffffffffffffffffffffff1660809073ffffffffffffffffffffffffffffffffffffffff1681525034801561004357600080fd5b50608051610b6561007b600039600081816101ec0152818161044a0152818161046e0152818161061801526106f70152610b656000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c8063b4449c301161005b578063b4449c3014610127578063ba45b0b814610143578063c4a85bc11461015f578063c651631f1461017b57610088565b806314afd79e1461008d57806356305831146100bd5780636a627842146100ed5780638da5cb5b14610109575b600080fd5b6100a760048036038101906100a291906109dc565b610197565b6040516100b49190610a18565b60405180910390f35b6100d760048036038101906100d291906109dc565b6101ca565b6040516100e49190610a4e565b60405180910390f35b610107600480360381019061010291906109dc565b6101ea565b005b610111610448565b60405161011e9190610a18565b60405180910390f35b610141600480360381019061013c91906109dc565b61046c565b005b61015d60048036038101906101589190610a69565b610542565b005b610179600480360381019061017491906109dc565b610616565b005b61019560048036038101906101909190610a69565b6106f5565b005b60016020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006020528060005260406000206000915054906101000a900460ff1681565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461026f576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b60006102796107df565b9050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036102b8576102b7610aa9565b5b600073ffffffffffffffffffffffffffffffffffffffff16600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161461035457610353610aa9565b5b81600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167f3b5c651dbcca6f936576130d201fbc5f5a2c3568820a6d9b2987ea2b7fc91b3260405160405180910390a45050565b7f000000000000000000000000000000000000000000000000000000000000000081565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146104f1576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6000808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81549060ff021916905550565b803373ffffffffffffffffffffffffffffffffffffffff16600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610607576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b61061183836107f1565b505050565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461069b576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b60016000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415801561079a57506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16155b156107d1576040517fc0185c6400000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6107db82826107f1565b5050565b60006107e9610949565b60601c905090565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905082600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167f3b5c651dbcca6f936576130d201fbc5f5a2c3568820a6d9b2987ea2b7fc91b3260405160405180910390a4505050565b6000424460405160200161095e929190610b03565b60405160208183030381529060405280519060200120905090565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006109a98261097e565b9050919050565b6109b98161099e565b81146109c457600080fd5b50565b6000813590506109d6816109b0565b92915050565b6000602082840312156109f2576109f1610979565b5b6000610a00848285016109c7565b91505092915050565b610a128161099e565b82525050565b6000602082019050610a2d6000830184610a09565b92915050565b60008115159050919050565b610a4881610a33565b82525050565b6000602082019050610a636000830184610a3f565b92915050565b60008060408385031215610a8057610a7f610979565b5b6000610a8e858286016109c7565b9250506020610a9f858286016109c7565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052600160045260246000fd5b6000819050919050565b6000819050919050565b610afd610af882610ad8565b610ae2565b82525050565b6000610b0f8285610aec565b602082019150610b1f8284610aec565b602082019150819050939250505056fea264697066735822122050c029738e9537d286e4d06e565a871770678727082bf0d2696a9e813602575964736f6c63430008130033";

    @Deprecated
    protected Item(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Item(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Item(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Item(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static Item load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Item(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Item load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Item(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Item load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Item(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Item load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Item(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Item> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Item.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Item> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Item.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Item> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Item.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Item> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Item.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.item = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.item = (String) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> allowAccess(String token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ALLOWACCESS,
                Collections.singletonList(new Address(160, token)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> disallowAccess(String token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISALLOWACCESS,
                Collections.singletonList(new Address(160, token)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> isAllowAccess(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISALLOWACCESS,
                Collections.singletonList(new Address(160, param0)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINT,
                Collections.singletonList(new Address(160, to)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_OWNER,
                Collections.emptyList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> ownerOf(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_OWNEROF,
                Collections.singletonList(new Address(160, param0)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, String item) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.Address(160, item)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferForce(String to, String item) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFORCE,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.Address(160, item)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public String item;
    }
}
