package shark

import shark.DiffingHeapGrowthDetector.HeapDumpAfterLoopingScenario

class LoopingHeapDumper(
  private val maxHeapDumps: Int,
  private val heapGraphProvider: HeapGraphProvider,
  private val scenarioLoopsPerDump: Int = 1,
) {

  init {
    check(maxHeapDumps >= 2) {
      "There should be at least 2 heap dumps"
    }
    check(scenarioLoopsPerDump >= 1) {
      "There should be at least 1 scenario loop per dump"
    }
  }

  // todo name repeat?
  fun dumpHeapRepeated(
    repeatedScenario: () -> Unit,
  ): Sequence<HeapDumpAfterLoopingScenario> {

    val heapDumps = (1..maxHeapDumps).asSequence().map {
      repeat(scenarioLoopsPerDump) {
        repeatedScenario()
      }
      HeapDumpAfterLoopingScenario(heapGraphProvider.openHeapGraph(), scenarioLoopsPerDump)
    }
    return heapDumps
  }
}