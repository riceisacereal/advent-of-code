using System.Collections;
using System.Text.RegularExpressions;

// 419 too high

// 455 too low

namespace Day02
{
  internal class Program
  {
    private static void Main(string[] args)
    {
      const string path = "../../../input.txt";

      int solution = -1;
      try
      {
        string[] allLines = File.ReadAllLines(path);
        solution = Solve(allLines, 2);
      }
      catch (Exception e)
      {
        Console.WriteLine("Exception: " + e.Message);
      }

      Console.WriteLine(solution);
    }

    private static int Solve(string[] input, int solvePart)
    {
      int numLines = input.Length;
      ArrayList lines = new ArrayList();

      for (int i = 0; i < numLines; i++)
      {
        List<int> line = Regex.Split(input[i], " ").Select(n => Int32.Parse(n)).ToList();
        lines.Add(line);
      }

      return solvePart == 1 ? SolvePartOne(numLines, lines) : SolvePartTwo(numLines, lines);
    }

    private static bool isSafe(List<int> line)
    {
      int order = line[0] - line[1];
      if (Math.Abs(order) > 3 || Math.Abs(order) < 1)
        return false;

      for (int i = 1; i < line.Count - 1; i++)
      {
        int prev = line[i];
        int next = line[i + 1];

        if (order * (prev - next) <= 0 || Math.Abs(prev - next) > 3)
        {
          //Console.WriteLine(String.Join(' ', line));
          return false;
        }
      }

      return true;
    }

    private static int SolvePartOne(int numLines, ArrayList lines)
    {
      int safeReportCount = 0;
      foreach (List<int> line in lines)
      {
        safeReportCount += isSafe(line) ? 1 : 0;
      }

      return safeReportCount;
    }

    private static int SolvePartTwo(int numLines, ArrayList lines)
    {
      int safeReportCount = 0;
      foreach (List<int> line in lines)
      {
        if (isSafe(line))
        {
          safeReportCount++;
          continue;
        }

        int safeCount = 0;
        for (int i = 0; i < line.Count; i++)
        {
          int[] subReport = new int[line.Count - 1];
          for (int j = 0, k = 0; j < line.Count - 1; j++, k++)
          {
            subReport[j] = k == i ? line[++k] : line[k];
          }

          if (isSafe(subReport.ToList()))
          {
            Console.WriteLine(String.Join(' ', subReport));
          }
          safeCount += isSafe(subReport.ToList()) ? 1 : 0;
        }
        safeReportCount += safeCount >= 1 ? 1 : 0;
      }

      return safeReportCount;
    }
  }
}