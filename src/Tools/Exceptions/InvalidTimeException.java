package Tools.Exceptions;

public class InvalidTimeException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidTimeException()
	{
		super();
	}

	public InvalidTimeException(String message)
	{
		super(message);
	}
}
