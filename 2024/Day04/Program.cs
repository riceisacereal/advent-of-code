using System.Collections;
using System.Text.RegularExpressions;

namespace Day04
{
  internal class Program
  {
    public class XMASCheck
    {
      public static string XMAS = "XMAS";
      public static Dictionary<string, int[]> DirectionalDisplacement = new()
      {
        {"N", new[] {0, -1}},
        {"NE", new[] {1, -1}},
        {"E", new[] {1, 0}},
        {"SE", new[] {1, 1}},
        {"S", new[] {0, 1}},
        {"SW", new[] {-1, 1}},
        {"W", new[] {-1, 0}},
        {"NW", new[] {-1, -1}}
      };

      public int XMASIndex { get; set; }
      public int MapIndexX { get; set; }
      public int MapIndexY { get; set; }
      public string Direction { get; set; }

      //public XMASCheck()
      //{
      //  XMASIndex = 0;
      //  Direction = string.Empty;
      //}

      public XMASCheck(string direction, int mapIndexX, int mapIndexY)
        : this(0, direction, mapIndexX, mapIndexY){ }

      public XMASCheck(int xmasIndex, string direction, int mapIndexX, int mapIndexY)
      {
        XMASIndex = xmasIndex;
        Direction = direction;
        MapIndexX = mapIndexX;
        MapIndexY = mapIndexY;
      }

      public bool CheckXMAS(string[] lines)
      {
        //int[] displacement = DirectionalDisplacement[Direction];
        //int newX = x + displacement[0];
        //int newY = y + displacement[1];
        if (MapIndexX < 0 || MapIndexX >= lines[0].Length || MapIndexY < 0 || MapIndexY >= lines.Length)
        {
          return false;
        }
        return lines[MapIndexY][MapIndexX] == XMAS[XMASIndex];
      }

      public void NextXMASCheck()
      {
        int[] displacement = DirectionalDisplacement[Direction];
        MapIndexX += displacement[0];
        MapIndexY += displacement[1];
        XMASIndex++;
      }
    }

    private static void Main(string[] args)
    {
      const string path = "../../../input.txt";

      int solution = -1;
      string[] allLines = [];
      try
      {
        allLines = File.ReadAllLines(path);
      }
      catch (Exception e)
      {
        Console.WriteLine("Exception: " + e.Message);
      }

      solution = Solve(allLines, 1);
      Console.WriteLine(solution);
    }

    private static int Solve(string[] input, int solvePart)
    {
      return solvePart == 1 ? SolvePartOne(input) : SolvePartTwo(input);
    }

    private static void EnqueueXMASChecks(Queue<XMASCheck> xmasQueue, int x, int y)
    {
      string[] directions = ["N", "NE", "E", "SE", "S", "SW", "W", "NW"];
      foreach (string direction in directions)
      {
        xmasQueue.Enqueue(new XMASCheck(direction, x, y));
      }
    }

    private static int SolvePartOne(string[] lines)
    {
      // For every X found, check in 8 directions to see if it makes XMAS
      // Make object for each direction to check, add in queue?
      int maxX = lines[0].Length;
      int maxY = lines.Length;

      int countXMAS = 0;
      for (int y = 0; y < maxY; y++)
      {
        for (int x = 0; x < maxX; x++)
        {
          Queue<XMASCheck> xmasQueue = new();

          char c = lines[y][x];
          if (c == 'X')
          {
            EnqueueXMASChecks(xmasQueue, x, y);
          }

          while (xmasQueue.Count > 0)
          {
            XMASCheck xmasCheck = xmasQueue.Dequeue();
            if (xmasCheck.CheckXMAS(lines))
            {
              xmasCheck.NextXMASCheck();
              int end = XMASCheck.XMAS.Length;
              if (xmasCheck.XMASIndex == end)
              {
                countXMAS++;
              } else
              {
                xmasQueue.Enqueue(xmasCheck);
              }
            }
          }
        }
      }

      return countXMAS;
    }

    private static int SolvePartTwo(string[] lines)
    {
      int sum = 0;
      bool enabled = true;
      foreach (string line in lines)
      {
        MatchCollection muls = Regex.Matches(line, @"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)");
        foreach (Match m in muls)
        {
          if (m.Value == "do()")
          {
            enabled = true;
          }
          else if (m.Value == "don't()")
          {
            enabled = false;
          }
          else if (enabled)
          {
            sum += Int32.Parse(m.Groups[1].Value) * Int32.Parse(m.Groups[2].Value);
          }
        }
      }

      return sum;
    }
  }
}