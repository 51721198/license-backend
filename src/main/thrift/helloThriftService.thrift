include "Model.thrift"

namespace java com.vico.license.service


service HelloWorldService {

  Model.Person sayHello(1:required string name);

}