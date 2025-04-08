package org.example.contract;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Collections;

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
public class AccessControl extends Contract {
    public static final String FUNC_ALLOWACCESS = "allowAccess";
    public static final String FUNC_DISALLOWACCESS = "disallowAccess";
    public static final String FUNC_ISALLOWACCESS = "isAllowAccess";
    public static final String FUNC_OWNER = "owner";
    private static final String BINARY = "0x60a06040523373ffffffffffffffffffffffffffffffffffffffff1660809073ffffffffffffffffffffffffffffffffffffffff1681525034801561004357600080fd5b506080516103f661006c6000396000818160f90152818161011d01526101f301526103f66000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806356305831146100515780638da5cb5b14610081578063b4449c301461009f578063c4a85bc1146100bb575b600080fd5b61006b60048036038101906100669190610333565b6100d7565b604051610078919061037b565b60405180910390f35b6100896100f7565b60405161009691906103a5565b60405180910390f35b6100b960048036038101906100b49190610333565b61011b565b005b6100d560048036038101906100d09190610333565b6101f1565b005b60006020528060005260406000206000915054906101000a900460ff1681565b7f000000000000000000000000000000000000000000000000000000000000000081565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146101a0576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6000808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81549060ff021916905550565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610276576040517f49e27cff00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b60016000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610300826102d5565b9050919050565b610310816102f5565b811461031b57600080fd5b50565b60008135905061032d81610307565b92915050565b600060208284031215610349576103486102d0565b5b60006103578482850161031e565b91505092915050565b60008115159050919050565b61037581610360565b82525050565b6000602082019050610390600083018461036c565b92915050565b61039f816102f5565b82525050565b60006020820190506103ba6000830184610396565b9291505056fea26469706673582212203896d13eacfec2b420b37e64a42362ec0f61951f31809f169de8ba2d898ea1e564736f6c63430008130033";

    @Deprecated
    protected AccessControl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AccessControl(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AccessControl(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AccessControl(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static AccessControl load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new AccessControl(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AccessControl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AccessControl(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AccessControl load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AccessControl(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AccessControl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AccessControl(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AccessControl> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AccessControl.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AccessControl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AccessControl.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<AccessControl> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AccessControl.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<AccessControl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AccessControl.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteFunctionCall<TransactionReceipt> allowAccess(String token) {
        final Function function = new Function(
                FUNC_ALLOWACCESS,
                Collections.singletonList(new Address(160, token)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> disallowAccess(String token) {
        final Function function = new Function(
                FUNC_DISALLOWACCESS,
                Collections.singletonList(new Address(160, token)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> isAllowAccess(String param0) {
        final Function function = new Function(
                FUNC_ISALLOWACCESS,
                Collections.singletonList(new Address(160, param0)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> owner() {
        final Function function = new Function(
                FUNC_OWNER,
                Collections.emptyList(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }
}
