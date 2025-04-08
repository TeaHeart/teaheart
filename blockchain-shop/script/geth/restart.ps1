$rootPath = $pwd

$env:PATH = $env:PATH + ";C:\Program Files\Geth"

$dataPath = "$rootPath\script\geth"

Get-Process geth -ErrorAction SilentlyContinue | Stop-Process
geth --datadir "$dataPath" --networkid 15 --dev --mine --miner.threads 1 --http --http.api web3,eth,debug,personal,net --http.corsdomain="package://6fd22d6fe5549ad4c4d8fd3ca0b7816b.mod" --vmdebug
