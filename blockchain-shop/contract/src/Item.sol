pragma solidity >=0.8.0;

import "./Random.sol";
import "./AccessControl.sol";

/**
 * @title 物品
 */
contract Item is AccessControl {
    /**
     * @dev 物品转让事件
     * @param from 发起者
     * @param to 接受者
     * @param item 转让的物品
     */
    event Transfer(
        address indexed from,
        address indexed to,
        address indexed item
    );

    /**
     * @dev 物品地址 到 物品所有者 的映射
     */
    mapping(address => address) public ownerOf;

    /**
     * @dev 铸造物品给 to
     * @param to 接收者
     */
    function mint(address to) public verifyOwner {
        address item = Random.nextAddress();
        assert(item != address(0));
        assert(ownerOf[item] == address(0));
        ownerOf[item] = to;
        emit Transfer(address(0), to, item);
    }

    /**
     * @dev 将物品 item 的所有权给 to, 任何人可以调用, 会先验证该物品的所有者是否为调用者(msg.sender)
     * @param to 接收者
     * @param item 转让的物品
     */
    function transfer(address to, address item) public verifyItemOwner(item) {
        _transfer(to, item);
    }

    /**
     * @dev 强制转让, 将物品 item 的所有权给 to, 只有 owner 和 isAllowAccess 被授权的可以调用, 不验证所有者
     * @param to 接收者
     * @param item 转让的物品
     */
    function transferForce(address to, address item) public verifyAccess {
        _transfer(to, item);
    }

    /**
     * @dev 实际转让操作
     * @param to 接收者
     * @param item 转让的物品
     */
    function _transfer(address to, address item) private {
        address from = ownerOf[item];
        ownerOf[item] = to;
        emit Transfer(from, to, item);
    }

    /**
     * @dev 验证物品的所有者是否为调用者(msg.sender), 不是抛出一个 InvalidOwner
     * @param item 验证的物品
     */
    modifier verifyItemOwner(address item) {
        if (ownerOf[item] != msg.sender) {
            revert InvalidOwner();
        }
        _;
    }
}
