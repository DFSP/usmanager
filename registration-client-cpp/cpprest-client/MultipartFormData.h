/**
 * Registration-client-api
 * Interage com o registration server (eureka) para registar esta instância ou obter servidores com o qual pode comunicar
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator 2.4.17.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

/*
 * MultipartFormData.h
 *
 * This class represents a container for building a application/x-multipart-formdata requests.
 */

#ifndef IO_SWAGGER_CLIENT_MODEL_MultipartFormData_H_
#define IO_SWAGGER_CLIENT_MODEL_MultipartFormData_H_


#include "IHttpBody.h"
#include "HttpContent.h"

#include <vector>
#include <map>
#include <memory>

#include <cpprest/details/basic_types.h>


namespace io {
namespace swagger {
namespace client {
namespace model {

class  MultipartFormData
    : public IHttpBody
{
public:
    MultipartFormData();
    MultipartFormData(const utility::string_t& boundary);
    virtual ~MultipartFormData();

    virtual void add( std::shared_ptr<HttpContent> content );
    virtual utility::string_t getBoundary();
    virtual std::shared_ptr<HttpContent> getContent(const utility::string_t& name) const;
    virtual bool hasContent(const utility::string_t& name) const;
    virtual void writeTo( std::ostream& target );

protected:
    std::vector<std::shared_ptr<HttpContent>> m_Contents;
    utility::string_t m_Boundary;
    std::map<utility::string_t, std::shared_ptr<HttpContent>> m_ContentLookup;
};

}
}
}
}

#endif /* IO_SWAGGER_CLIENT_MODEL_MultipartFormData_H_ */
