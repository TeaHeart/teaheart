pragma solidity >=0.8.0;

import "./AccessControl.sol";
import "./Coin.sol";
import "./Item.sol";

/**
 * @title 商店, 依赖 coin 和 item
 */
contract Shop is AccessControl {
    /**
     * @dev 如果该商品的 onSale 为 false(未上架或不存在), 抛出这个错误
     */
    error NotForSale();

    /**
     * @dev 商品上架事件
     * @param seller 卖家
     * @param item 商品
     * @param price 价格
     */
    event Put(address indexed seller, address indexed item, uint256 price);

    /**
     * @dev 商品下架事件
     * @param seller 卖家
     * @param item 商品
     */
    event Pull(address indexed seller, address indexed item);

    /**
     * @dev 商品购买事件
     * @param seller 卖家
     * @param buyers 买家
     * @param item 商品
     * @param price 价格
     */
    event Bought(
        address indexed seller,
        address indexed buyers,
        address indexed item,
        uint256 price
    );

    /**
     * @dev 商品结构体
     * @param seller 卖家
     * @param price 价格
     * @param onSale 是否在售(用于解决0地址和未上架物品的安全问题)
     */
    struct Goods {
        address seller;
        uint256 price;
        bool onSale;
    }

    /**
     * @dev 卖家 到 商品 的映射
     */
    mapping(address => Goods) public goodsOf;

    Coin _coin;
    Item _item;

    constructor(address coin, address item) {
        _coin = Coin(coin);
        _item = Item(item);
    }

    /**
     * @dev 上架一个物品, 任何人可以调用, 会验证该物品的所有者是否为调用者(msg.sender)
     * @param item 上架的物品
     * @param price 价格
     */
    function put(address item, uint256 price) public verifyItemOwner(item) {
        _put(msg.sender, item, price);
    }

    /**
     * @dev 上架一个物品, 只有 owner 和 isAllowAccess 被授权的可以调用, 不会验证该物品的所有者
     * @param item 上架的物品
     * @param price 价格
     */
    function putForce(address item, uint256 price) public verifyAccess {
        _put(_item.ownerOf(item), item, price);
    }

    /**
     * @dev 实际上架物品的操作
     * @param seller 卖家
     * @param item 上架的物品
     * @param price 价格
     */
    function _put(address seller, address item, uint256 price) private {
        goodsOf[item] = Goods(seller, price, true);
        emit Put(seller, item, price);
    }

    /**
     * @dev 下架一个物品, 任何人可以调用, 会验证该物品的所有者是否为调用者(msg.sender)
     * @param item 下架的物品
     */
    function pull(address item) public verifyItemOwner(item) {
        _pull(msg.sender, item);
    }

    /**
     * @dev 下架一个物品, 只有 owner 和 isAllowAccess 被授权的可以调用, 不会验证该物品的所有者
     * @param item 下架的物品
     */
    function pullForce(address item) public verifyAccess {
        _pull(goodsOf[item].seller, item);
    }

    /**
     * @dev 实际下架物品的操作
     * @param seller 卖家
     * @param item 上架的物品
     */
    function _pull(address seller, address item) private {
        delete goodsOf[item];
        emit Pull(seller, item);
    }

    /**
     * @dev 购买一个物品, 任何人可以调用
     * @param item 购买的物品
     */
    function buy(address item) public {
        Goods storage goods = goodsOf[item];
        _buy(goods.seller, msg.sender, item, goods.price, goods.onSale);
    }

    /**
     * @dev 强制购买一个物品, 只有 owner 和 isAllowAccess 被授权的可以调用
     * @param buyers 买家
     * @param item 购买的物品
     */
    function buyForce(address buyers, address item) public verifyAccess {
        Goods storage goods = goodsOf[item];
        _buy(goods.seller, buyers, item, goods.price, goods.onSale);
    }

    /**
     * @dev 实际下架物品的操作, 会验证商品是否有效(onSale 为 true), 无效抛出一个 NotForSale
     * @param seller 卖家
     * @param buyers 买家
     * @param item 商品
     * @param price 价格
     * @param onSale 是否在售
     */
    function _buy(
        address seller,
        address buyers,
        address item,
        uint256 price,
        bool onSale
    ) private verifyItemStatus(onSale) {
        _coin.transferForce(buyers, seller, price);
        _item.transferForce(buyers, item);
        _pull(seller, item);
        emit Bought(seller, buyers, item, price);
    }

    /**
     * @dev 验证物品的所有者是否为调用者(msg.sender), 不是抛出一个 InvalidOwner
     * @param item 验证的物品
     */
    modifier verifyItemOwner(address item) {
        if (_item.ownerOf(item) != msg.sender) {
            revert InvalidOwner();
        }
        _;
    }

    /**
     * @dev 验证一个商品是否在售, 未售抛出一个 NotForSale
     * @param onSale 是否在售
     */
    modifier verifyItemStatus(bool onSale) {
        if (!onSale) {
            revert NotForSale();
        }
        _;
    }
}
