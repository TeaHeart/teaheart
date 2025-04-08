package org.example.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class Shop extends Contract {
    public static final String FUNC_ALLOWACCESS = "allowAccess";
    public static final String FUNC_BUY = "buy";
    public static final String FUNC_BUYFORCE = "buyForce";
    public static final String FUNC_DISALLOWACCESS = "disallowAccess";
    public static final String FUNC_GOODSOF = "goodsOf";
    public static final String FUNC_ISALLOWACCESS = "isAllowAccess";
    public static final String FUNC_OWNER = "owner";
    public static final String FUNC_PULL = "pull";
    public static final String FUNC_PULLFORCE = "pullForce";
    public static final String FUNC_PUT = "put";
    public static final String FUNC_PUTFORCE = "putForce";
    public static final Event BOUGHT_EVENT = new Event("Bought",
            Arrays.asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    public static final Event PULL_EVENT = new Event("Pull",
            Arrays.asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    public static final Event PUT_EVENT = new Event("Put",
            Arrays.asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    private static final String BINARY = "0x60a06040523373ffffffffffffffffffffffffffffffffffffffff1660809073ffffffffffffffffffffffffffffffffffffffff168152503480156200004457600080fd5b50604051620014233803806200142383398181016040528101906200006a91906200015e565b81600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050620001a5565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006200012682620000f9565b9050919050565b620001388162000119565b81146200014457600080fd5b50565b60008151905062000158816200012d565b92915050565b60008060408385031215620001785762000177620000f4565b5b6000620001888582860162000147565b92505060206200019b8582860162000147565b9150509250929050565b60805161123f620001e460003960008181610210015281816104a90152818161062f0152818161088b0152818161090601526109dc015261123f6000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80637f8d53c6116100715780637f8d53c61461014e5780638da5cb5b1461016a5780639b5c7ee514610188578063b4449c30146101ba578063c4a85bc1146101d6578063f088d547146101f2576100a9565b806341a494a3146100ae57806352d11238146100ca57806356305831146100e65780636b445f94146101165780636f64076414610132575b600080fd5b6100c860048036038101906100c39190610fc3565b61020e565b005b6100e460048036038101906100df9190611003565b610378565b005b61010060048036038101906100fb9190611003565b610487565b60405161010d919061104b565b60405180910390f35b610130600480360381019061012b919061109c565b6104a7565b005b61014c60048036038101906101479190611003565b61062d565b005b6101686004803603810190610163919061109c565b610778565b005b610172610889565b60405161017f91906110eb565b60405180910390f35b6101a2600480360381019061019d9190611003565b6108ad565b6040516101b193929190611115565b60405180910390f35b6101d460048036038101906101cf9190611003565b610904565b005b6101f060048036038101906101eb9190611003565b6109da565b005b61020c60048036038101906102079190611003565b610ab9565b005b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580156102b357506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16155b156102ea576040517fc0185c6400000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090506103738160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16848484600101548560020160009054906101000a900460ff16610b46565b505050565b803373ffffffffffffffffffffffffffffffffffffffff16600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166314afd79e836040518263ffffffff1660e01b81526004016103eb91906110eb565b602060405180830381865afa158015610408573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061042c9190611161565b73ffffffffffffffffffffffffffffffffffffffff1614610479576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6104833383610d2c565b5050565b60006020528060005260406000206000915054906101000a900460ff1681565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415801561054c57506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16155b15610583576040517fc0185c6400000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b610629600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166314afd79e846040518263ffffffff1660e01b81526004016105e191906110eb565b602060405180830381865afa1580156105fe573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906106229190611161565b8383610e0f565b5050565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580156106d257506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16155b15610709576040517fc0185c6400000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b610775600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1682610d2c565b50565b813373ffffffffffffffffffffffffffffffffffffffff16600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166314afd79e836040518263ffffffff1660e01b81526004016107eb91906110eb565b602060405180830381865afa158015610808573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061082c9190611161565b73ffffffffffffffffffffffffffffffffffffffff1614610879576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b610884338484610e0f565b505050565b7f000000000000000000000000000000000000000000000000000000000000000081565b60016020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020160009054906101000a900460ff16905083565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610989576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6000808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81549060ff021916905550565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a5f576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b60016000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209050610b428160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16338484600101548560020160009054906101000a900460ff16610b46565b5050565b8080610b7e576040517f1d99ddbf00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16638fbcf8148688866040518463ffffffff1660e01b8152600401610bdd9392919061118e565b600060405180830381600087803b158015610bf757600080fd5b505af1158015610c0b573d6000803e3d6000fd5b50505050600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663c651631f86866040518363ffffffff1660e01b8152600401610c6c9291906111c5565b600060405180830381600087803b158015610c8657600080fd5b505af1158015610c9a573d6000803e3d6000fd5b50505050610ca88685610d2c565b8373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff168773ffffffffffffffffffffffffffffffffffffffff167ffa945eefd5c2ddf8beb4fb7e15e2b5f2f295ba939c584a8a849e199aa767860c86604051610d1c91906111ee565b60405180910390a4505050505050565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff021916905560018201600090556002820160006101000a81549060ff021916905550508073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167fbfcbd10b20f52fbc564f5e382621a7185258b114ae4cf592da05cd7cd59a82b460405160405180910390a35050565b60405180606001604052808473ffffffffffffffffffffffffffffffffffffffff16815260200182815260200160011515815250600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020160006101000a81548160ff0219169083151502179055509050508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167fdcb17cc96d0e89a597bc656a61aa693823021479cd3a14ed51d1a44f2ae294ec83604051610f5391906111ee565b60405180910390a3505050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610f9082610f65565b9050919050565b610fa081610f85565b8114610fab57600080fd5b50565b600081359050610fbd81610f97565b92915050565b60008060408385031215610fda57610fd9610f60565b5b6000610fe885828601610fae565b9250506020610ff985828601610fae565b9150509250929050565b60006020828403121561101957611018610f60565b5b600061102784828501610fae565b91505092915050565b60008115159050919050565b61104581611030565b82525050565b6000602082019050611060600083018461103c565b92915050565b6000819050919050565b61107981611066565b811461108457600080fd5b50565b60008135905061109681611070565b92915050565b600080604083850312156110b3576110b2610f60565b5b60006110c185828601610fae565b92505060206110d285828601611087565b9150509250929050565b6110e581610f85565b82525050565b600060208201905061110060008301846110dc565b92915050565b61110f81611066565b82525050565b600060608201905061112a60008301866110dc565b6111376020830185611106565b611144604083018461103c565b949350505050565b60008151905061115b81610f97565b92915050565b60006020828403121561117757611176610f60565b5b60006111858482850161114c565b91505092915050565b60006060820190506111a360008301866110dc565b6111b060208301856110dc565b6111bd6040830184611106565b949350505050565b60006040820190506111da60008301856110dc565b6111e760208301846110dc565b9392505050565b60006020820190506112036000830184611106565b9291505056fea26469706673582212204be126d2172b793297949d955474b1eef15f84395869ceea1d1eee4a587a4f3564736f6c63430008130033";

    @Deprecated
    protected Shop(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Shop(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Shop(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Shop(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static Shop load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Shop(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Shop load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Shop(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Shop load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Shop(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Shop load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Shop(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Shop> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String coin, String item) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, coin),
                new org.web3j.abi.datatypes.Address(160, item)));
        return deployRemoteCall(Shop.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Shop> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String coin, String item) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, coin),
                new org.web3j.abi.datatypes.Address(160, item)));
        return deployRemoteCall(Shop.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Shop> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String coin, String item) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, coin),
                new org.web3j.abi.datatypes.Address(160, item)));
        return deployRemoteCall(Shop.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Shop> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String coin, String item) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, coin),
                new org.web3j.abi.datatypes.Address(160, item)));
        return deployRemoteCall(Shop.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<BoughtEventResponse> getBoughtEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BOUGHT_EVENT, transactionReceipt);
        ArrayList<BoughtEventResponse> responses = new ArrayList<BoughtEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BoughtEventResponse typedResponse = new BoughtEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyers = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.item = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BoughtEventResponse> boughtEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BoughtEventResponse>() {
            @Override
            public BoughtEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BOUGHT_EVENT, log);
                BoughtEventResponse typedResponse = new BoughtEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.buyers = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.item = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BoughtEventResponse> boughtEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BOUGHT_EVENT));
        return boughtEventFlowable(filter);
    }

    public List<PullEventResponse> getPullEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PULL_EVENT, transactionReceipt);
        ArrayList<PullEventResponse> responses = new ArrayList<PullEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PullEventResponse typedResponse = new PullEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.item = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PullEventResponse> pullEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PullEventResponse>() {
            @Override
            public PullEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PULL_EVENT, log);
                PullEventResponse typedResponse = new PullEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.item = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PullEventResponse> pullEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PULL_EVENT));
        return pullEventFlowable(filter);
    }

    public List<PutEventResponse> getPutEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUT_EVENT, transactionReceipt);
        ArrayList<PutEventResponse> responses = new ArrayList<PutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PutEventResponse typedResponse = new PutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.item = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PutEventResponse> putEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PutEventResponse>() {
            @Override
            public PutEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUT_EVENT, log);
                PutEventResponse typedResponse = new PutEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.item = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PutEventResponse> putEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUT_EVENT));
        return putEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> allowAccess(String token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ALLOWACCESS,
                Collections.singletonList(new Address(160, token)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> buy(String item) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUY,
                Collections.singletonList(new Address(160, item)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> buyForce(String buyers, String item) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BUYFORCE,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, buyers),
                        new org.web3j.abi.datatypes.Address(160, item)),
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

    public RemoteFunctionCall<TransactionReceipt> goodsOf(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GOODSOF,
                Collections.singletonList(new Address(160, param0)),
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

    public RemoteFunctionCall<TransactionReceipt> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_OWNER,
                Collections.emptyList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> pull(String item) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PULL,
                Collections.singletonList(new Address(160, item)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> pullForce(String item) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PULLFORCE,
                Collections.singletonList(new Address(160, item)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> put(String item, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PUT,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, item),
                        new org.web3j.abi.datatypes.generated.Uint256(price)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> putForce(String item, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PUTFORCE,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, item),
                        new org.web3j.abi.datatypes.generated.Uint256(price)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static class BoughtEventResponse extends BaseEventResponse {
        public String seller;

        public String buyers;

        public String item;

        public BigInteger price;
    }

    public static class PullEventResponse extends BaseEventResponse {
        public String seller;

        public String item;
    }

    public static class PutEventResponse extends BaseEventResponse {
        public String seller;

        public String item;

        public BigInteger price;
    }
}
