package com.ibm.fhir.ohp.api.allg;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallets;

import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.DefaultQueryHandlers;

public class BlockchainClient {
	private String CHANNEL_NAME = "mychannel";
	private String CHAINCODE_NAME = "simple";
	// change <USER DIRECTORY> to your user directory name
	private String WALLET_PATH = "/home/<USER DIRECTORY>/mywork/vars/profiles/vscode/wallets";
	private String CONFIG_PATH = "/home/<USER DIRECTORY>/mywork/vars/profiles/mychannel_connection_for_javasdk.yaml";
	private String USERID_NAME = "org0-example-com"; // TODO figure out this value

	Gateway gateway = null;
	Contract contract = null;

	private BlockchainClient() {
		try {
			if (gateway == null) {
				gateway = getGateway();

				Network network = gateway.getNetwork(CHANNEL_NAME);
				contract = network.getContract(CHAINCODE_NAME);

				System.out.println(
						"-----------------------------------------------------------------------------------------");
				System.out.println(
						"                 Successfully connected to the Blockchain                                ");
				System.out.println(
						"-----------------------------------------------------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(
					"------------------------------------- ERROR ---------------------------------------------");
			System.out.println(
					"                       Failed to connect to the Blockchain                               ");
			System.out.println(
					"------------------------------------- ERROR ---------------------------------------------");
		}
	}

    private volatile static BlockchainClient uniqueInstance;
    public static BlockchainClient getInstance(){
        if(uniqueInstance == null){
            synchronized (BlockchainClient.class){
                if(uniqueInstance == null){
                    uniqueInstance = new BlockchainClient();
                }
            }
        }
        return uniqueInstance;
    }

	public Gateway getGateway() throws Exception {
		var walletPath = Paths.get(WALLET_PATH);
		System.out.println("walletpath: " + walletPath);
		var wallet = Wallets.newFileSystemWallet(walletPath);
		var configPath = Paths.get(CONFIG_PATH);
		System.out.println("configpath: " + configPath);
		var builder = Gateway.createBuilder();
		builder.identity(wallet, USERID_NAME).networkConfig(configPath);
		builder.queryHandler(DefaultQueryHandlers.MSPID_SCOPE_ROUND_ROBIN);

		return builder.connect();
	}

	public String submitTransaction(String action, String resourceType, String body) {
		byte[] result;
		String transactionName = resourceType + ':' + action;
		try {
			result = contract.submitTransaction(transactionName, body);
			return new String(result);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
