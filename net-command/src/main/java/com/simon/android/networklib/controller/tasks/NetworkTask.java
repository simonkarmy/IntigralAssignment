package com.simon.android.networklib.controller.tasks;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.simon.android.networklib.controller.NetworkLib;
import com.simon.android.networklib.controller.utils.NetworkUtil;

/**
 *
 * Created by Simon simonkarmy2004@gmail.com on 11/4/2015
 *
 * Abstract class that represents any task will be sent to the network
 * We applied Command Pattern in our network layer to retrieve data from server
 * So each server API call will has a different implementation of NetworkTask (EX. LoadFlightsTask)
 *
 * Each task will send a specific implementation from a Command
 *
 * This class contains the common implementation and management of the Async server loading
 * with only abstract methods that will be used to perform the network action doNetworkAction()
 *
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
abstract public class NetworkTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private TaskRequestID taskRequestTag;
    private Exception exception;
    private boolean isComplete = false;
    private boolean isAborted = false;


    private OnCompleteListener<Result> completeListener;
    private OnExceptionListener exceptionListener;

    public TaskRequestID getTaskRequestTag() {
        return taskRequestTag;
    }

    public void setTaskRequestTag(TaskRequestID taskRequestTag) {
        this.taskRequestTag = taskRequestTag;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isAborted() {
        return isAborted;
    }

    public void setOnCompleteListener(OnCompleteListener<Result> completeListener) {
        this.completeListener = completeListener;
    }

    /**
     * This listener gets called if any error happens. It's a convenience method to
     * catch all the errors in 1 shot.
     *
     * @param l
     */
    public void setOnExceptionListener(OnExceptionListener l) {
        this.exceptionListener = l;
    }

    public NetworkTask() {
        super();
    }

    /**
     * A convenience method used to hide the poor API of the internal execute method that can't be overridden.
     */
    @SuppressWarnings("unchecked")
    public void execute() {
        execute(null, null);
    }

    /**
     * Silly AsynTask has the cancel marked as final. Use abort instead;
     */
    public void abort() {
        isAborted = true;
        cancelCommands();
        cancel(true);
    }

    /**
     * This is where we make the network call. We're not passing object here, so this method must get the params
     * it needs from the class properties. Since this is thread be sure to make as volatile if needed.
     *
     * @return actual model results from the server
     * @throws Exception
     */
    abstract protected Result doNetworkAction(Params... params) throws Exception;

    /**
     * A method will be called before cancelling the task to notify command to be canceled
     */
    abstract protected void cancelCommands();

    /**
     * This method runs on the UI Thread.
     * Use this hook for what happens when the doNetworkAction method returns successfully.
     *
     * @param result The result from doNetworkAction
     */
    protected void onPostSuccess(Result result) {
    }

    protected void onPostFault(Exception e) {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isComplete = false;
        isAborted = false;
        boolean hasNetworkConnection = NetworkUtil.hasInternetAccess(NetworkLib.getContext());
        if (!hasNetworkConnection) {
            exception = new NetworkErrorException("Internet connection unavailable");
            if (exceptionListener != null) {
                exceptionListener.onException(exception);
            }
            abort();
        }
    }

    /**
     * Mostly likely you should not override this. It's not marked as final, but treat it like that.
     */
    @Override
    protected Result doInBackground(Params... params) {
        if (isCancelled()) {
            return null;
        }
        try {
            return doNetworkAction(params);
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
            return null;
        }
    }

    /**
     * Out logic to figure what kind of result we got.
     */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        isComplete = true;
        if (isCancelled() || isAborted()) {
            return;
        }

        if (exception != null) {
            onPostFault(exception);
            if (exceptionListener != null)
                exceptionListener.onException(exception);
        }

        // SUCCESS!
        else {
            onPostSuccess(result);
            if (completeListener != null) {
                completeListener.onComplete(result);
            }
        }
    }

    public interface OnCompleteListener<Result> {
        void onComplete(Result result);
    }

    public interface OnExceptionListener {
        void onException(Exception exception);
    }
}
