    package org.example;

    import lombok.AllArgsConstructor;
    import lombok.Data;

    import java.util.List;

    public class ReduceBooleanList {

        public static void main(String[] args) {
            // Assuming boolean list represent batch result
            List<Boolean> batchResult = List.of(true, false, true, false);

            // To get the status of all batches from a list you need to REDUCE more values to one
            BatchStatus statusOfAllBatches = batchResult.stream()
                    // 1st: map the boolean to a BatchResponse that represent the status of single batch
                    .map(BatchStatus::new)
                    // 2nd: combine the list of BatchResponse to a single pojo, combining values to represent the status of all batches
                    .reduce(
                            /* Identity: initial value */ new BatchStatus("true", 0, 0, 0),
                            /* Accumulator: function for combining two values */ BatchStatus::merge
                    );

            System.out.println(statusOfAllBatches);
        }

        @Data
        @AllArgsConstructor
        static class BatchStatus {
            private String batchCompleted;
            private Integer totalCount;
            private Integer processed;
            private Integer pending;

            public BatchStatus(Boolean result) {
                batchCompleted = String.valueOf(result);
                totalCount = 1;
                processed = result != null && result ? 1 : 0;
                pending = result == null || !result ? 0 : 1;

            }

            public BatchStatus merge(BatchStatus b) {
                batchCompleted = String.valueOf(Boolean.parseBoolean(this.batchCompleted) && Boolean.parseBoolean(b.batchCompleted));
                totalCount += b.getTotalCount();
                processed += b.getProcessed();
                pending += b.getPending();
                return this;
            }
        }
    }
