package ru.antomad.otus.hw06.atm;

import ru.antomad.otus.hw06.holder.Holder;
import ru.antomad.otus.hw06.holder.PaperHolder;
import ru.antomad.otus.hw06.paper.*;

import java.util.ArrayList;
import java.util.List;

public class AtmService implements Atm {

    private final PaperHolder<OneHundred> oneHundredPaperHolder = new PaperHolder<>();
    private final PaperHolder<TwoHundred> twoHundredPaperHolder = new PaperHolder<>();
    private final PaperHolder<FiveHundred> fiveHundredPaperHolder = new PaperHolder<>();
    private final PaperHolder<OneThousand> oneThousandPaperHolder = new PaperHolder<>();
    private final PaperHolder<TwoThousand> twoThousandPaperHolder = new PaperHolder<>();
    private final PaperHolder<FiveThousand> fiveThousandPaperHolder = new PaperHolder<>();

    @Override
    public void put(List<Paper> papers) {
        sortAndPut(papers);
    }

    @Override
    public List<Paper> pull(long sum) {
        return prepareBySum(sum);
    }

    @Override
    public long balance() {
        return calcBalance();
    }

    private void sortAndPut(List<Paper> papers) {
        for (Paper paper : papers) {
            if (paper instanceof OneHundred) {
                oneHundredPaperHolder.put((OneHundred) paper);
            } else if (paper instanceof TwoHundred) {
                twoHundredPaperHolder.put((TwoHundred) paper);
            } else if (paper instanceof FiveHundred) {
                fiveHundredPaperHolder.put((FiveHundred) paper);
            } else if (paper instanceof OneThousand) {
                oneThousandPaperHolder.put((OneThousand) paper);
            } else if (paper instanceof TwoThousand) {
                twoThousandPaperHolder.put((TwoThousand) paper);
            } else if (paper instanceof FiveThousand) {
                fiveThousandPaperHolder.put((FiveThousand) paper);
            }
        }
    }

    private long calcBalance() {
        return 100L * oneHundredPaperHolder.count() +
                200L * twoHundredPaperHolder.count() +
                500L * fiveHundredPaperHolder.count() +
                1000L * oneThousandPaperHolder.count() +
                2000L * twoThousandPaperHolder.count() +
                5000L * fiveThousandPaperHolder.count();
    }

    private List<Paper> prepareBySum(long sum) {
        List<Paper> result = new ArrayList<>();

        CalcResult fiveThousandResult = fetchFromHolder(fiveThousandPaperHolder, 5000, sum);
        CalcResult twoThousandResult = fetchFromHolder(twoThousandPaperHolder, 2000, fiveThousandResult.getSum());
        CalcResult oneThousandResult = fetchFromHolder(oneThousandPaperHolder, 1000, twoThousandResult.getSum());
        CalcResult fiveHundredResult = fetchFromHolder(fiveHundredPaperHolder, 500, oneThousandResult.getSum());
        CalcResult twoHundredResult = fetchFromHolder(twoHundredPaperHolder, 200, fiveHundredResult.getSum());
        CalcResult oneHundredResult = fetchFromHolder(oneHundredPaperHolder, 100, twoHundredResult.getSum());

        result.addAll(fiveThousandResult.getPapers());
        result.addAll(twoThousandResult.getPapers());
        result.addAll(oneThousandResult.getPapers());
        result.addAll(fiveHundredResult.getPapers());
        result.addAll(twoHundredResult.getPapers());
        result.addAll(oneHundredResult.getPapers());

        if (oneHundredResult.getSum() > 0) {
            sortAndPut(result);
            throw new RuntimeException("Couldn't find required sum");
        } else {
            return result;
        }
    }

    private CalcResult fetchFromHolder(Holder<? extends Paper> holder, int amount, long sum) {
        List<Paper> papers = new ArrayList<>();
        while (sum >= amount) {
            Paper paper = holder.get();
            if (paper != null) {
                papers.add(paper);
                sum = sum - amount;
            } else {
                break;
            }
        }
        return new CalcResult(sum, papers);
    }

    private static class CalcResult {
        private final long sum;
        private final List<Paper> papers;

        public CalcResult(long sum, List<Paper> papers) {
            this.sum = sum;
            this.papers = papers;
        }

        public long getSum() {
            return sum;
        }

        public List<Paper> getPapers() {
            return papers;
        }
    }
}