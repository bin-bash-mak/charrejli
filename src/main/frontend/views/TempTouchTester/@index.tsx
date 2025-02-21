import { Button, PasswordField, ProgressBar, TextField } from '@vaadin/react-components';
import { Editor } from '@monaco-editor/react';
import { useForm } from '@vaadin/hilla-react-form';
import { HelloEndpoint } from 'Frontend/generated/endpoints';
import React, { useState } from 'react';
import TouchNumberDetails
  from 'Frontend/generated/com/mohammadalikassem/charrejli/modules/parsers/lb/touch/models/TouchNumberDetails';

const TempTouchTesterView: React.FC = () => {


  const [res, setRes] = useState<null | { raw: string, parsed:TouchNumberDetails }>();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const t = async () => {

    const t = await HelloEndpoint.tempGetTouchNumberDetails('', '');
    if (t) {

    }
  };
  return <div>
    <div className={'p-m m-m flex md:flex gap-m'}>


      <TextField value={username} onChange={(e) => setUsername(e.target.value)} />
      <PasswordField value={password} onChange={e => setPassword(e.target.value)} />
      <Button onClick={async () => {
        setLoading(true);
        try {
          const res = await HelloEndpoint.tempGetTouchNumberDetails(username, password)
          setRes({raw:"", parsed: res})
        } catch (e) {
        } finally {
        setLoading(false);
        }


      }} color={'primary'}>Test</Button>
    </div>

    {loading ? <ProgressBar /> : null}

    {res ? <div className="max-w-full max-h-full" style={{ width: '80vw', height: '70vh' }}>
      <Editor className={'max-w-full max-h-full'}
              theme={'vs-dark'}
              language={"json"}
              value={JSON.stringify(res.parsed, null, 2)}

      />
    </div> : null}

  </div>;
};


export default TempTouchTesterView;