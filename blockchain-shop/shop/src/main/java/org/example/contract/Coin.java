package org.example.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
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
public class Coin extends Contract {
    public static final String FUNC_ALLOWACCESS = "allowAccess";
    public static final String FUNC_BALANCEOF = "balanceOf";
    public static final String FUNC_DISALLOWACCESS = "disallowAccess";
    public static final String FUNC_ISALLOWACCESS = "isAllowAccess";
    public static final String FUNC_MINT = "mint";
    public static final String FUNC_OWNER = "owner";
    public static final String FUNC_TRANSFER = "transfer";
    public static final String FUNC_TRANSFERFORCE = "transferForce";
    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    private static final String BINARY = "0x60a06040523373ffffffffffffffffffffffffffffffffffffffff1660809073ffffffffffffffffffffffffffffffffffffffff1681525034801561004357600080fd5b50608051610a2b61007b60003960008181610199015281816103160152818161033a01528181610435015261050b0152610a2b6000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80638fbcf8141161005b5780638fbcf81414610127578063a9059cbb14610143578063b4449c301461015f578063c4a85bc11461017b57610088565b806340c10f191461008d57806356305831146100a957806370a08231146100d95780638da5cb5b14610109575b600080fd5b6100a760048036038101906100a29190610814565b610197565b005b6100c360048036038101906100be9190610854565b6102dc565b6040516100d0919061089c565b60405180910390f35b6100f360048036038101906100ee9190610854565b6102fc565b60405161010091906108c6565b60405180910390f35b610111610314565b60405161011e91906108f0565b60405180910390f35b610141600480360381019061013c919061090b565b610338565b005b61015d60048036038101906101589190610814565b610424565b005b61017960048036038101906101749190610854565b610433565b005b61019560048036038101906101909190610854565b610509565b005b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461021c576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b80600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825461026b919061098d565b925050819055508173ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef836040516102d091906108c6565b60405180910390a35050565b60006020528060005260406000206000915054906101000a900460ff1681565b60016020528060005260406000206000915090505481565b7f000000000000000000000000000000000000000000000000000000000000000081565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580156103dd57506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16155b15610414576040517fc0185c6400000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b61041f8383836105e8565b505050565b61042f3383836105e8565b5050565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146104b8576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6000808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81549060ff021916905550565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461058e576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b60016000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b828180600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610663576040517ff4d678b800000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b82600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282546106b291906109c1565b9250508190555082600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254610708919061098d565b925050819055508373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8560405161076c91906108c6565b60405180910390a35050505050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006107ab82610780565b9050919050565b6107bb816107a0565b81146107c657600080fd5b50565b6000813590506107d8816107b2565b92915050565b6000819050919050565b6107f1816107de565b81146107fc57600080fd5b50565b60008135905061080e816107e8565b92915050565b6000806040838503121561082b5761082a61077b565b5b6000610839858286016107c9565b925050602061084a858286016107ff565b9150509250929050565b60006020828403121561086a5761086961077b565b5b6000610878848285016107c9565b91505092915050565b60008115159050919050565b61089681610881565b82525050565b60006020820190506108b1600083018461088d565b92915050565b6108c0816107de565b82525050565b60006020820190506108db60008301846108b7565b92915050565b6108ea816107a0565b82525050565b600060208201905061090560008301846108e1565b92915050565b6000806000606084860312156109245761092361077b565b5b6000610932868287016107c9565b9350506020610943868287016107c9565b9250506040610954868287016107ff565b9150509250925092565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610998826107de565b91506109a3836107de565b92508282019050808211156109bb576109ba61095e565b5b92915050565b60006109cc826107de565b91506109d7836107de565b92508282039050818111156109ef576109ee61095e565b5b9291505056fea2646970667358221220180cfdf8240ef89e741fb547d0c0c29a5ab6ae8f0ce9f83e1d1bb1fab5c1ba0364736f6c63430008130033";

    @Deprecated
    protected Coin(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Coin(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Coin(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Coin(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static Coin load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Coin(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Coin load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Coin(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Coin load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Coin(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Coin load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Coin(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Coin> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Coin.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Coin> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Coin.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Coin> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Coin.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Coin> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Coin.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public RemoteFunctionCall<TransactionReceipt> balanceOf(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_BALANCEOF,
                Collections.singletonList(new Address(160, param0)),
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

    public RemoteFunctionCall<TransactionReceipt> mint(String to, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINT,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.generated.Uint256(amount)),
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

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferForce(String from, String to, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFORCE,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, from),
                        new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger amount;
    }
}
