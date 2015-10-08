package app.nichepro.util;

public interface AbstractAsyncTaskCallable<ParameterT, ProgressT, ReturnT> {
    public ReturnT call(AbstractAsyncTask<ParameterT, ProgressT, ReturnT> task) throws Exception;
}
