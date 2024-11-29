using System;
using System.IO;

namespace MyApp
{
  internal class Program
  {
    static void Main(string[] args)
    {
      String line;
      try
      {
        StreamReader sr = new StreamReader("../../../test.txt");
        line = sr.ReadLine();

        while (line != null)
        {
          Console.WriteLine(line);
          line = sr.ReadLine();
        }

        sr.Close();
        Console.ReadLine();
      }
      catch (Exception e)
      {
        Console.WriteLine("Exception: " + e.Message);
      }
    }
  }
}