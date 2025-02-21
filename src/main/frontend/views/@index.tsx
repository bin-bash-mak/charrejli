import { AccountsCrudService } from 'Frontend/generated/endpoints';
import type { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { AutoCrud } from '@vaadin/hilla-react-crud';
import AccountModel from 'Frontend/generated/com/mohammadalikassem/charrejli/accounts/entities/AccountModel';
import { _meta } from '@vaadin/hilla-lit-form';
import { PasswordField } from '@vaadin/react-components';

export const config: ViewConfig = {
  menu: {
    title: 'Main page'
  }
};

export default function MainView() {
  return (
    <div>
      <AutoCrud service={AccountsCrudService} model={AccountModel} gridProps={{
        visibleColumns: [ 'number', 'active', 'balance', 'validity', 'lastUpdatedAt',"operator", 'status'],
        title: 'Accounts'


      }}
                formProps={{
                  visibleFields: ["number","username",'password',"balance", 'active', 'operator', "status"],
                  fieldOptions: { 'password': { element: <PasswordField /> } },
                }}
      />

    </div>
  );
}
