using System.Collections;
using System.Text.RegularExpressions;

namespace Day03
{
  internal class Program
  {
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

      solution = Solve(allLines, 2);
      Console.WriteLine(solution);
    }

    private static int Solve(string[] input, int solvePart)
    {
      int numLines = input.Length;
      return solvePart == 1 ? SolvePartOne(numLines, input) : SolvePartTwo(numLines, input);
    }

    private static int SolvePartOne(int numLines, string[] lines)
    {
      int sum = 0;
      foreach (string line in lines)
      {
        int muls = Regex.Matches(line, @"mul\((\d{1,3}),(\d{1,3})\)")
          .Select(match => Int32.Parse(match.Groups[1].Value) * Int32.Parse(match.Groups[2].Value)).Aggregate((a, b) => a + b);
        sum += muls;
      }

      return sum;
    }

    private static int SolvePartTwo(int numLines, string[] lines)
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
          } else if (m.Value == "don't()")
          {
            enabled = false;
          } else if (enabled)
          {
            sum += Int32.Parse(m.Groups[1].Value) * Int32.Parse(m.Groups[2].Value);
          }
        }
      }

      return sum;
    }
  }
}