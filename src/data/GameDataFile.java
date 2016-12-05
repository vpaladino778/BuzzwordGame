package data;

import com.fasterxml.jackson.core.*;
import components.AppDataComponent;
import components.AppFileComponent;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by vpala on 11/8/2016.
 */
public class GameDataFile implements AppFileComponent{

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String WORD_LEVELS = "WORD_LEVELS";
    public static final String ANIMAL_LEVELS = "ANIMAL_LEVELS";
    public static final String PEOPLE_LEVELS = "PEOPLE_LEVELS";


    /*Saves a profiles data */
    @Override
    public void saveData(AppDataComponent data, Path filePath) throws IOException {
        Profile profile = (Profile) data;
        ArrayList<Level> wordLevels = ((Profile) data).getWordLevelsCompleted();
        ArrayList<Level> animalLevels = ((Profile) data).getAnimalLevelsCompleted();
        ArrayList<Level> peopleLevels = ((Profile) data).getPeopleLevelsCompleted();

        JsonFactory jsonFactory = new JsonFactory();

        try(OutputStream out = Files.newOutputStream(filePath)){
            JsonGenerator generator = jsonFactory.createGenerator(out, JsonEncoding.UTF8);

            generator.writeStartObject();

            generator.writeStringField(USERNAME,profile.getUsername());
            generator.writeStringField(PASSWORD,profile.getPassword());

            generator.writeFieldName(WORD_LEVELS);
            generator.writeStartArray(wordLevels.size());
            for(Level lvl: wordLevels){
                generator.writeString(Integer.toString(lvl.getLevelID()));
            }
            generator.writeEndArray();

            generator.writeFieldName(ANIMAL_LEVELS);
            generator.writeStartArray(animalLevels.size());
            for(Level lvl: animalLevels){
                generator.writeString(Integer.toString(lvl.getLevelID()));
            }
            generator.writeEndArray();

            generator.writeFieldName(PEOPLE_LEVELS);
            generator.writeStartArray(peopleLevels.size());
            for(Level lvl: peopleLevels){
                generator.writeString(Integer.toString(lvl.getLevelID()));
            }
            generator.writeEndArray();

            generator.writeEndObject();

            generator.close();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public Profile loadData(Path filePath) throws IOException {
        Profile gameData = new Profile();
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(Files.newInputStream(filePath));

        while(!jsonParser.isClosed()){
            JsonToken token = jsonParser.nextToken();

            if(token == null)
                break;

             if(JsonToken.FIELD_NAME.equals(token)){
                String fieldname = jsonParser.getCurrentName();

                switch(fieldname){
                    case USERNAME:
                        jsonParser.nextToken();
                        System.out.println("Username: " + jsonParser.getValueAsString());
                        gameData.setUsername(jsonParser.getValueAsString());
                        break;
                    case PASSWORD:
                        jsonParser.nextToken();
                        System.out.println("Password: " + jsonParser.getValueAsString());
                        gameData.setPassword(jsonParser.getValueAsString());
                        break;
                    case WORD_LEVELS:
                        jsonParser.nextToken();
                        while(jsonParser.nextToken() != JsonToken.END_ARRAY)
                            gameData.getWordLevelsCompleted().add(new Level(jsonParser.getIntValue(),7));
                        break;
                    case ANIMAL_LEVELS:
                        jsonParser.nextToken();
                        while(jsonParser.nextToken() != JsonToken.END_ARRAY)
                            gameData.getAnimalLevelsCompleted().add(new Level(jsonParser.getIntValue(),7));
                        break;
                    case PEOPLE_LEVELS:
                        jsonParser.nextToken();
                        while(jsonParser.nextToken() != JsonToken.END_ARRAY)
                            gameData.getPeopleLevelsCompleted().add(new Level(jsonParser.getIntValue(),7));
                        break;
                    default:
                        throw new JsonParseException(jsonParser, "Unable to load JSON data");
                }
            }
        }
        return gameData;
    }

    @Override
    public void exportData(AppDataComponent data, Path filePath) throws IOException {

    }
}
