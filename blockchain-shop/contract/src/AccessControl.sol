pragma solidity >=0.8.0;

import "./Ownable.sol";

/**
 * @title 访问控制, 用于控制哪些人可以调用
 */
contract AccessControl is Ownable {
    /**
     * @dev 若调用者不是 owner 或在 isAllowAccess 中被允许, 抛出这个错误
     */
    error InvalidAccess();

    /**
     * @dev // 用户 到 是否被授权 的映射
     */
    mapping(address => bool) public isAllowAccess;

    /**
     * @dev 将一个 address 加入 isAllowAccess, 只有 owner 可以调用
     * @param token 被加入的地址
     */
    function allowAccess(address token) public verifyOwner {
        isAllowAccess[token] = true;
    }

    /**
     * @dev 将一个 address 从 isAllowAccess 中移除, 只有 owner 可以调用
     * @param token 被移除的地址
     */
    function disallowAccess(address token) public verifyOwner {
        delete isAllowAccess[token];
    }

    /**
     * @dev 验证调用者是否有权访问被其修饰的函数, 无权访问抛出一个 InvalidAccess
     */
    modifier verifyAccess() {
        if (msg.sender != owner && !isAllowAccess[msg.sender]) {
            revert InvalidAccess();
        }
        _;
    }
}
