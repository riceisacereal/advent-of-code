using System.Text.RegularExpressions;

namespace Day01
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
      catch (Exception e) {
        Console.WriteLine("Exception: " + e.Message);
      }
      
      Console.WriteLine(solution);
    }

    private static int Solve(string[] input, int solvePart)
    {
      int numLines = input.Length;
      int[] left = new int[numLines];
      int[] right = new int[numLines];

      for (int i = 0; i < numLines; i++)
      {
        int[] pair = Regex.Split(input[i], " +").Select(n => Int32.Parse(n)).ToArray();
        left[i] = pair[0];
        right[i] = pair[1];
      }

      return solvePart == 1 ? SolvePartOne(numLines, left, right) : SolvePartTwo(numLines, left, right);
    }

    private static int SolvePartOne(int numLines, int[] left, int[] right)
    {
      Array.Sort(left);
      Array.Sort(right);

      int sumOfDistance = 0;
      for (int i = 0; i < numLines; i++)
      {
        sumOfDistance += Math.Abs(left[i] - right[i]);
      }

      return sumOfDistance;
    }
    
    private static int SolvePartTwo(int numLines, int[] left, int[] right)
    {
      Dictionary<int, int> rightCollection = new Dictionary<int, int>();
      
      foreach (int n in right)
      {
        if (!rightCollection.TryAdd(n, 1))
        {
          rightCollection[n] += 1;
        }
      }

      int similarityScore = 0;
      foreach (int n in left)
      {
        if (rightCollection.TryGetValue(n, out int count))
        {
          similarityScore += n * count;
        }
      }
      
      return similarityScore;
    }
  }
}