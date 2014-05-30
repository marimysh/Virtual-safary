import java.io.*;

public class WorkFile
{
  public static void read()
  {
    try
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("bd.txt")));
      String[] newAnimal;
      String stringNewAnimal;
      Animal value;
      while (reader.ready())
      {
        stringNewAnimal = reader.readLine();
        newAnimal = stringNewAnimal.split(";");
        switch (newAnimal[0])
        {
          case "Lion":
            value = new Lion(newAnimal);
            Animal.elements.addElement(value);
            Animal.mapPredator[value.getY() + 5][value.getX() + 9].add(value);
            break;
          case "Wolf":
            value = new Wolf(newAnimal);
            Animal.elements.addElement(value);
            Animal.mapPredator[value.getY() + 5][value.getX() + 9].add(value);
            break;
          case "Giraffe":
            value = new Giraffe(newAnimal);
            Animal.elements.addElement(value);
            Animal.mapHerbivores[value.getY() + 5][value.getX() + 9].add(value);
            break;
          case "Tiger":
            value = new Tiger(newAnimal);
            Animal.elements.addElement(value);
            Animal.mapPredator[value.getY() + 5][value.getX() + 9].add(value);
            break;
          case "Dog":
            value = new Dog(newAnimal);
            Animal.elements.addElement(value);
            Animal.mapPredator[value.getY() + 5][value.getX() + 9].add(value);
            break;
          case "Cat":
            value = new Cat(newAnimal);
            Animal.elements.addElement(value);
            Animal.mapPredator[value.getY() + 5][value.getX() + 9].add(value);
            break;
        }
      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void write(String value)
  {
    try
    {
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bd.txt", true)));
      out.println(value);
      out.close();
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void newFile()
  {
    try
    {
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bd.txt")));
      out.print(' ');
      out.close();
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
