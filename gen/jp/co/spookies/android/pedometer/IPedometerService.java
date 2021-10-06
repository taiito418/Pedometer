/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package jp.co.spookies.android.pedometer;
public interface IPedometerService extends android.os.IInterface
{
  /** Default implementation for IPedometerService. */
  public static class Default implements jp.co.spookies.android.pedometer.IPedometerService
  {
    /**
         * 歩数を取得
         * @return 歩数
         */
    @Override public int getStep() throws android.os.RemoteException
    {
      return 0;
    }
    /**
         * 歩いている時間を取得
         * @return 歩いている時間（秒）
         */
    @Override public int getWalkTime() throws android.os.RemoteException
    {
      return 0;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements jp.co.spookies.android.pedometer.IPedometerService
  {
    private static final java.lang.String DESCRIPTOR = "jp.co.spookies.android.pedometer.IPedometerService";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an jp.co.spookies.android.pedometer.IPedometerService interface,
     * generating a proxy if needed.
     */
    public static jp.co.spookies.android.pedometer.IPedometerService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof jp.co.spookies.android.pedometer.IPedometerService))) {
        return ((jp.co.spookies.android.pedometer.IPedometerService)iin);
      }
      return new jp.co.spookies.android.pedometer.IPedometerService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_getStep:
        {
          data.enforceInterface(descriptor);
          int _result = this.getStep();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_getWalkTime:
        {
          data.enforceInterface(descriptor);
          int _result = this.getWalkTime();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements jp.co.spookies.android.pedometer.IPedometerService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /**
           * 歩数を取得
           * @return 歩数
           */
      @Override public int getStep() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getStep, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getStep();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /**
           * 歩いている時間を取得
           * @return 歩いている時間（秒）
           */
      @Override public int getWalkTime() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getWalkTime, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getWalkTime();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static jp.co.spookies.android.pedometer.IPedometerService sDefaultImpl;
    }
    static final int TRANSACTION_getStep = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getWalkTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    public static boolean setDefaultImpl(jp.co.spookies.android.pedometer.IPedometerService impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static jp.co.spookies.android.pedometer.IPedometerService getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  /**
       * 歩数を取得
       * @return 歩数
       */
  public int getStep() throws android.os.RemoteException;
  /**
       * 歩いている時間を取得
       * @return 歩いている時間（秒）
       */
  public int getWalkTime() throws android.os.RemoteException;
}
