pragma solidity >=0.8.0;

/**
 * @title 可拥有的合约, 用于访问控制
 */
contract Ownable {
    /**
     * @dev 若调用者不是 owner, 抛出这个错误
     */
    error InvalidOwner();

    /**
     * @dev 合约的所有者
     */
    address public immutable owner = msg.sender;

    /**
     * @dev 验证调用者是否是 owner, 不是抛出一个 InvalidOwner
     */
    modifier verifyOwner() {
        if (msg.sender != owner) {
            revert InvalidOwner();
        }
        _;
    }
}
