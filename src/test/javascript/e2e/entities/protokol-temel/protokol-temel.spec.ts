import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ProtokolTemelComponentsPage from './protokol-temel.page-object';
import ProtokolTemelUpdatePage from './protokol-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('ProtokolTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let protokolTemelComponentsPage: ProtokolTemelComponentsPage;
  let protokolTemelUpdatePage: ProtokolTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    protokolTemelComponentsPage = new ProtokolTemelComponentsPage();
    protokolTemelComponentsPage = await protokolTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load ProtokolTemels', async () => {
    expect(await protokolTemelComponentsPage.title.getText()).to.match(/Protokol Temels/);
    expect(await protokolTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ProtokolTemels', async () => {
    const beforeRecordsCount = (await isVisible(protokolTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(protokolTemelComponentsPage.table);
    protokolTemelUpdatePage = await protokolTemelComponentsPage.goToCreateProtokolTemel();
    await protokolTemelUpdatePage.enterData();
    expect(await isVisible(protokolTemelUpdatePage.saveButton)).to.be.false;

    expect(await protokolTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(protokolTemelComponentsPage.table);
    await waitUntilCount(protokolTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await protokolTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await protokolTemelComponentsPage.deleteProtokolTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(protokolTemelComponentsPage.records, beforeRecordsCount);
      expect(await protokolTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(protokolTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
