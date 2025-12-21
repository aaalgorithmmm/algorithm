package 정렬;

import java.util.Arrays;

public class 정렬 {
  public static void main(String[] args) {
    int[] original = { 64, 34, 25, 12, 22, 11, 90, 5 };

    System.out.println("원본 배열: " + Arrays.toString(original));
    System.out.println();

    // 1. 버블 정렬
    int[] arr1 = original.clone();
    bubbleSort(arr1);
    System.out.println("버블 정렬: " + Arrays.toString(arr1));

    // 2. 선택 정렬
    int[] arr2 = original.clone();
    selectionSort(arr2);
    System.out.println("선택 정렬: " + Arrays.toString(arr2));

    // 3. 삽입 정렬
    int[] arr3 = original.clone();
    insertionSort(arr3);
    System.out.println("삽입 정렬: " + Arrays.toString(arr3));

    // 4. 퀵 정렬
    int[] arr4 = original.clone();
    quickSort(arr4);
    System.out.println("퀵 정렬: " + Arrays.toString(arr4));

    // 5. 병합 정렬
    int[] arr5 = original.clone();
    mergeSort(arr5);
    System.out.println("병합 정렬: " + Arrays.toString(arr5));

    // 6. 기수 정렬 (음수 없는 배열)
    int[] arr6 = original.clone();
    radixSortNonNegative(arr6);
    System.out.println("기수 정렬: " + Arrays.toString(arr6));
  }

  /**
   * 04-1 버블 정렬 (Bubble Sort)
   *
   * 개념: 인접한 두 원소를 비교해서 큰 값을 오른쪽으로 계속 밀어내는 정렬입니다.
   * 한 번의 "패스(pass)"가 끝나면 가장 큰 값이 맨 뒤에 확정됩니다.
   *
   * 동작 방식:
   * - 바깥 루프 'pass'는 정렬 패스 횟수
   * - 안쪽 루프는 'length - 1 - pass'까지만 비교 (뒤쪽은 이미 확정이라 제외)
   * - swap이 한 번도 일어나지 않으면 이미 정렬된 상태 → 조기 종료(swapped)
   *
   * 시간 복잡도: 평균/최악 O(n²), 최선(거의 정렬 + 조기 종료) O(n)
   * 공간 복잡도: O(1)
   * 안정 정렬: 가능 (인접 swap만 수행하므로 같은 값 순서 유지)
   */
  static void bubbleSort(int[] array) {
    int length = array.length;
    for (int pass = 0; pass < length - 1; pass++) {
      boolean swapped = false;
      for (int compareIndex = 0; compareIndex < length - 1 - pass; compareIndex++) {
        if (array[compareIndex] > array[compareIndex + 1]) {
          int temp = array[compareIndex];
          array[compareIndex] = array[compareIndex + 1];
          array[compareIndex + 1] = temp;
          swapped = true;
        }
      }
      if (!swapped)
        return; // 이미 정렬됨
    }
  }

  /**
   * 04-2 선택 정렬 (Selection Sort)
   *
   * 개념: 매 단계에서 가장 작은 값(min)을 찾아 현재 위치로 가져오는 정렬입니다.
   * 즉, i번째 자리에 들어갈 값을 "선택"합니다.
   *
   * 동작 방식:
   * - 'currentIndex' 위치에 올 최소값 인덱스 'minIndex'를 찾음
   * - 찾은 후 'currentIndex <-> minIndex' swap
   * - 이 과정을 끝까지 반복
   *
   * 시간 복잡도: 평균/최악/최선 O(n²) (항상 최소를 찾느라 비교를 많이 함)
   * 공간 복잡도: O(1)
   * 안정 정렬: 아님 (swap 때문에 같은 값의 상대 순서가 깨질 수 있음)
   * 특징: swap 횟수는 최대 n-1로 적은 편
   */
  static void selectionSort(int[] array) {
    for (int currentIndex = 0; currentIndex < array.length - 1; currentIndex++) {
      int minIndex = currentIndex;
      for (int searchIndex = currentIndex + 1; searchIndex < array.length; searchIndex++) {
        if (array[searchIndex] < array[minIndex])
          minIndex = searchIndex;
      }
      int temp = array[currentIndex];
      array[currentIndex] = array[minIndex];
      array[minIndex] = temp;
    }
  }

  /**
   * 04-3 삽입 정렬 (Insertion Sort)
   *
   * 개념: 카드 정렬처럼, 왼쪽 구간을 항상 정렬된 상태로 유지하면서
   * 새 원소를 알맞은 위치에 끼워 넣는(insertion) 방식입니다.
   *
   * 동작 방식:
   * - 'currentIndex=1'부터 시작해 'key=array[currentIndex]'를 잡음
   * - 왼쪽(정렬된 구간)에서 'key'보다 큰 값들을 오른쪽으로 밀어 공간 확보
   * - 확보된 위치에 'key'를 삽입
   *
   * 시간 복잡도: 평균/최악 O(n²), 최선(이미 정렬/거의 정렬) O(n)에 가까움
   * 공간 복잡도: O(1)
   * 안정 정렬: 맞음 ('array[insertPosition] > key' 조건이라 같은 값은 밀지 않음)
   */
  static void insertionSort(int[] array) {
    for (int currentIndex = 1; currentIndex < array.length; currentIndex++) {
      int key = array[currentIndex];
      int insertPosition = currentIndex - 1;
      while (insertPosition >= 0 && array[insertPosition] > key) {
        array[insertPosition + 1] = array[insertPosition];
        insertPosition--;
      }
      array[insertPosition + 1] = key;
    }
  }

  /**
   * 04-4 퀵 정렬 (Quick Sort)
   *
   * 개념: 피벗(pivot)을 기준으로 원소들을 왼쪽(작은 값)/오른쪽(큰 값)으로 분할(partition)한 뒤
   * 각 구간을 재귀적으로 정렬하는 분할정복 알고리즘입니다.
   *
   * 동작 방식:
   * - pivot: 'array[(left + right) >>> 1]' (가운데 값)
   * - 'leftPtr'는 왼쪽에서 pivot 이상을 찾고, 'rightPtr'는 오른쪽에서 pivot 이하를 찾음
   * - 두 포인터가 교차하기 전까지 swap하며 partition 수행
   * - 이후 '(left ~ rightPtr)', '(leftPtr ~ right)' 두 구간을 재귀 호출
   *
   * 시간 복잡도: 평균 O(n log n), 최악 O(n²) (피벗 선택이 계속 나쁘면)
   * 공간 복잡도: O(log n) (재귀 호출 스택)
   * 안정 정렬: 아님 (swap으로 같은 값 순서가 깨질 수 있음)
   */
  static void quickSort(int[] array) {
    quickSort(array, 0, array.length - 1);
  }

  static void quickSort(int[] array, int left, int right) {
    if (left >= right)
      return;

    int pivot = array[(left + right) >>> 1];
    int leftPtr = left, rightPtr = right;

    while (leftPtr <= rightPtr) {
      while (array[leftPtr] < pivot)
        leftPtr++;
      while (array[rightPtr] > pivot)
        rightPtr--;
      if (leftPtr <= rightPtr) {
        int temp = array[leftPtr];
        array[leftPtr] = array[rightPtr];
        array[rightPtr] = temp;
        leftPtr++;
        rightPtr--;
      }
    }
    if (left < rightPtr)
      quickSort(array, left, rightPtr);
    if (leftPtr < right)
      quickSort(array, leftPtr, right);
  }

  /**
   * 04-5 병합 정렬 (Merge Sort)
   *
   * 개념: 배열을 반으로 나누어 각각 정렬한 뒤,
   * 정렬된 두 배열을 병합(merge)해서 전체를 정렬하는 분할정복 알고리즘입니다.
   *
   * 동작 방식:
   * - 'mergeSort(array, tempArray, left, right)'로 재귀 분할
   * - 'merge()'에서 두 포인터('leftIndex', 'rightIndex')로 작은 값을 tempArray에 순서대로 복사
   * - merge 완료 후 tempArray 구간을 원본 배열로 복사
   *
   * 시간 복잡도: 평균/최악/최선 O(n log n) (항상 일정)
   * 공간 복잡도: O(n) (tempArray 필요)
   * 안정 정렬: 맞음 (merge 시 'array[leftIndex] <= array[rightIndex]'로 동일 값이면 왼쪽을 먼저 선택)
   */
  static void mergeSort(int[] array) {
    if (array.length < 2)
      return;
    int[] tempArray = new int[array.length];
    mergeSort(array, tempArray, 0, array.length - 1);
  }

  static void mergeSort(int[] array, int[] tempArray, int left, int right) {
    if (left >= right)
      return;
    int mid = (left + right) >>> 1;
    mergeSort(array, tempArray, left, mid);
    mergeSort(array, tempArray, mid + 1, right);
    merge(array, tempArray, left, mid, right);
  }

  static void merge(int[] array, int[] tempArray, int left, int mid, int right) {
    int leftIndex = left, rightIndex = mid + 1, mergeIndex = left;
    while (leftIndex <= mid && rightIndex <= right)
      tempArray[mergeIndex++] = (array[leftIndex] <= array[rightIndex]) ? array[leftIndex++] : array[rightIndex++];
    while (leftIndex <= mid)
      tempArray[mergeIndex++] = array[leftIndex++];
    while (rightIndex <= right)
      tempArray[mergeIndex++] = array[rightIndex++];
    for (int copyIndex = left; copyIndex <= right; copyIndex++)
      array[copyIndex] = tempArray[copyIndex];
  }

  /**
   * 04-6 기수 정렬 (Radix Sort) - 음수 없는 int[]
   *
   * 개념: 비교 기반 정렬이 아니라, 숫자를 자릿수(1의 자리 → 10의 자리 → 100의 자리...) 기준으로
   * 여러 번 정렬하여 최종적으로 전체 정렬을 완성하는 방식입니다.
   * 각 자릿수 정렬은 계수 정렬(Counting Sort)을 안정적으로 수행해야 합니다.
   *
   * 동작 방식 (LSD 방식):
   * - 'exponent=1,10,100...' 순서로 자릿수 증가
   * - 현재 자릿수 digit = '(currentValue / exponent) % 10'
   * - 'digitCount[10]'으로 빈도 계산 → prefix sum으로 누적 위치 계산
   * - 뒤에서부터 sortedArray에 채움으로 안정성 확보
   * - sortedArray를 원본으로 복사 후 다음 자릿수 진행
   *
   * 시간 복잡도: O(d * (n + k)) (d: 자릿수 개수, k: 진법(10))
   * 공간 복잡도: O(n + k) (sortedArray + digitCount)
   * 안정 정렬: 맞음 (뒤에서 채우는 stable counting sort 구조)
   * 제약: 현재 구현은 음수 없음 전제
   */
  static void radixSortNonNegative(int[] array) {
    if (array.length == 0)
      return;

    int max = array[0];
    for (int currentValue : array)
      if (currentValue > max)
        max = currentValue;

    int exponent = 1; // 1, 10, 100...
    int[] sortedArray = new int[array.length];

    while (max / exponent > 0) {
      int[] digitCount = new int[10];

      // 1) count
      for (int currentValue : array)
        digitCount[(currentValue / exponent) % 10]++;

      // 2) prefix sum (안정 정렬 위해)
      for (int digitIndex = 1; digitIndex < 10; digitIndex++)
        digitCount[digitIndex] += digitCount[digitIndex - 1];

      // 3) 뒤에서부터 채우기(안정성 핵심)
      for (int index = array.length - 1; index >= 0; index--) {
        int digitValue = (array[index] / exponent) % 10;
        sortedArray[--digitCount[digitValue]] = array[index];
      }

      // 4) 복사
      System.arraycopy(sortedArray, 0, array, 0, array.length);

      exponent *= 10;
    }
  }
}