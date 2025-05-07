export const mapStatus = (status) => {
  switch (status) {
    case "SUCCESS":
      return "done";
    case "INSUFFICIENT":
      return "progress";
    case "FAILURE":
      return "fail";
    default:
      return "";
  }
};
