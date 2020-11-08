// 27.12.2019
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.io.File;
import org.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Test {

	public static void main(String args[]) {
		FunBegins();
	}

	public static void FunBegins() {

		JSONParser parser = new JSONParser();
        try
        {
            Object object = parser
                    .parse(new FileReader("test.json"));

            //convert Object to JSONObject
            JSONObject jsonObject = (JSONObject)object;

            Long initialMoney =  (Long)jsonObject.get("initialMoney");
           Long howManyTaxedCells = (Long) jsonObject.get("howManyTaxedCells");
            Long taxedMoneyAmount = (Long) jsonObject.get("taxedMoneyAmount");
           Long numberOfPlayers = (Long) jsonObject.get("numberOfPlayers");

            //Reading the array
            JSONArray nameOfPlayersJson = (JSONArray)jsonObject.get("nameOfPlayers");
            String[] nameOfPlayers = new String[numberOfPlayers.intValue()];

            JSONArray takingOfRiskJson = (JSONArray) jsonObject.get("takingOfRisk");
            int[] takingOfRisk = new int[numberOfPlayers.intValue()];

            for(int i = 0; i< numberOfPlayers; i++)
            {
            	nameOfPlayers[i] = (String)nameOfPlayersJson.get(i);
            }
            //System.out.println(takingOfRiskJson.toString());
            for(int i = 0; i< numberOfPlayers; i++)
            {
            	String a = takingOfRiskJson.get(i).toString();
            	takingOfRisk[i] = Integer.parseInt(a);

            }
            //take cells inputs
            JSONArray cell_inputs_Json = (JSONArray)jsonObject.get("cell_inputs");
            String[] cell_inputs = new String[40];
            
            for(int i = 0; i< 40; i++)
            {
            	cell_inputs[i] = (String)cell_inputs_Json.get(i);
            	System.out.println(cell_inputs[i]);
            }
           
            //Printing all the values

            System.out.println("initialMoney: " + initialMoney);
            System.out.println("howManyTaxedCells: " + howManyTaxedCells);
            System.out.println("taxedMoneyAmount: " + taxedMoneyAmount);
           System.out.println("numberOfPlayers: " + numberOfPlayers);

           System.out.println("nameOfPlayers:");

                System.out.println(nameOfPlayersJson.toString());
                int initial = initialMoney.intValue();
                for(int i = 0; i< numberOfPlayers; i++)
                {
                	System.out.println("risk "+i+" = "+takingOfRisk[i]);
                }
                Game Monopoly = new Game(initial, howManyTaxedCells.intValue(), nameOfPlayers, taxedMoneyAmount.intValue(),takingOfRisk,cell_inputs);
                Monopoly.Play();

        }
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


	}

}
