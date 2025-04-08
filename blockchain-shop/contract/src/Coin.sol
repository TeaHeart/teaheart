pragma solidity >=0.8.0;

import "./AccessControl.sol";

/**
 * @title 代币合约
 */
contract Coin is AccessControl {
    /**
     * @dev 当余额不足时, 抛出这个错误
     */
    error InsufficientBalance();

    /**
     * @dev 代币转账事件
     * @param from 发起者
     * @param to 接收者
     * @param amount 转账代币数量
     */
    event Transfer(address indexed from, address indexed to, uint256 amount);

    /**
     * @dev 账户 到 余额 的映射
     */
    mapping(address => uint256) public balanceOf;

    /**
     * @dev 铸币, 只有 owner 可以调用, 给予 to amount 数量的代币
     * @param to 接收者
     * @param amount 代币数量
     */
    function mint(address to, uint256 amount) public verifyOwner {
        balanceOf[to] += amount;
        emit Transfer(address(0), to, amount);
    }

    /**
     * @dev 转账, 任何人可以调用
     * @param to 接收者
     * @param amount 代币数量
     */
    function transfer(address to, uint256 amount) public {
        _transfer(msg.sender, to, amount);
    }

    /**
     * @dev 强制转账, 只有 owner 和 isAllowAccess 被授权的可以调用
     * @param from 发起者
     * @param to 接收者
     * @param amount 代币数量
     */
    function transferForce(
        address from,
        address to,
        uint256 amount
    ) public verifyAccess {
        _transfer(from, to, amount);
    }

    /**
     * @dev 实际转账操作, 余额不足时抛出一个 InsufficientBalance
     * @param from 发起者
     * @param to 接收者
     * @param amount 代币数量
     */
    function _transfer(
        address from,
        address to,
        uint256 amount
    ) private verifyBalance(from, amount) {
        balanceOf[from] -= amount;
        balanceOf[to] += amount;
        emit Transfer(from, to, amount);
    }

    /**
     * @dev 验证一个账户的余额是否足够支持该次转账
     * @param from 发起者
     * @param amount 代币数量
     */
    modifier verifyBalance(address from, uint256 amount) {
        if (balanceOf[from] < amount) {
            revert InsufficientBalance();
        }
        _;
    }
}
