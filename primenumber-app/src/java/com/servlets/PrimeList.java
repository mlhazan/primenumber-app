package com.servlets;

import java.math.BigInteger;
import java.util.Vector;

/**
 *
 * @author Hasan
 */
class PrimeList implements Runnable {

     private Vector primesFound;
     private int numPrimes, numDigits;

     public PrimeList(int numPrimes, int numDigits, boolean runInBackground) {

          primesFound = new Vector(numPrimes);
          this.numPrimes = numPrimes;
          this.numDigits = numDigits;
          if (runInBackground) {
               Thread t = new Thread(this);
// Use low priority so you don’t slow down server.
               t.setPriority(Thread.MIN_PRIORITY);
               t.start();
          } else {
               run();
          }
     }

     public void run() {
          BigInteger start = Primes.random(numDigits);
          for (int i = 0; i < numPrimes; i++) {
               start = Primes.nextPrime(start);
               synchronized (this) {
                    primesFound.addElement(start);
               }
          }
     }

     public synchronized boolean isDone() {
          return (primesFound.size() == numPrimes);
     }

     public synchronized Vector getPrimes() {
          if (isDone()) {
               return (primesFound);
          } else {
               return ((Vector) primesFound.clone());
          }
     }

     public int numDigits() {
          return (numDigits);
     }

     public int numPrimes() {
          return (numPrimes);
     }

     public synchronized int numCalculatedPrimes() {
          return (primesFound.size());
     }
}
