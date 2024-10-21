package exceptions;


public class Exception extends RuntimeException {
	public Exception(String message) {
		super(message);
	}
	
	public Exception(String message,Throwable cause) {
		super(message,cause);
	}

	public static class UnexpectedCharacterException extends Exception{
		
		public UnexpectedCharacterException (String message) {
			super(message);
		}
		
		public UnexpectedCharacterException(String message,Throwable cause) {
			super(message,cause);
		}
	}
	
	public static class FileReadException extends Exception{
			
			public FileReadException (String message) {
				super(message);
			}
			
			public FileReadException(String message,Throwable cause) {
				super(message,cause);
			}
		}
	
	public static class FileWriteException extends Exception{
		
		public FileWriteException (String message) {
			super(message);
		}
		
		public FileWriteException(String message,Throwable cause) {
			super(message,cause);
		}
	}
	
	public static class NonPositiveValueException extends Exception {
        public NonPositiveValueException(String message) {
            super(message);
        }

        public NonPositiveValueException(String message, Throwable cause) {
            super(message, cause);
        }

        public String getMessage() {
            return super.getMessage() + "\nPlease enter a positive value.";
        }
    }
	
	public static class NonAppropriateValueException extends Exception {
        public NonAppropriateValueException(String message) {
            super(message);
        }

        public NonAppropriateValueException(String message, Throwable cause) {
            super(message, cause);
        }

        public String getMessage() {
            return super.getMessage() + "\nPlease enter a valid value.";
        }
    }
}