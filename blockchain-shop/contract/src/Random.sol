pragma solidity >=0.8.0;

/**
 * @title 随机数库, 用于生成随机的地址等
 */
library Random {
    /**
     * @dev 随机生成一个 address
     * @return 随机生成的 address
     */
    function nextAddress() internal view returns (address) {
        return address(bytes20(nextBytes32()));
    }

    /**
     * @dev 随机生成一个 bytes32
     * @return 随机生成的 bytes32
     */
    function nextBytes32() internal view returns (bytes32) {
        return keccak256(abi.encodePacked(block.timestamp, block.difficulty));
    }
}
